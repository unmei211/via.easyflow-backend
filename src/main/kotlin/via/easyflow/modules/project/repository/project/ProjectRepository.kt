package via.easyflow.modules.project.repository.project

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.project.repository.project.model.ProjectEntity
import via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity

@Repository
class ProjectRepository(
    private val client: DatabaseClient,
    private val objectMapper: ObjectMapper
) : via.easyflow.modules.project.repository.project.IProjectRepository {
    private val log = logger()
    override fun getProjects(): Flux<via.easyflow.modules.project.repository.project.model.ProjectEntity> {
        val sql = """
            SELECT document FROM project
        """.trimIndent()
        val projectEntityFlux = client.sql(sql)
            .fetch()
            .all()
            .retry(2)
            .map {
                objectMapper.readValue((it["document"] as Json).asString(), via.easyflow.modules.project.repository.project.model.ProjectEntity::class.java)
            }

        return projectEntityFlux
    }

    override fun existsProjectById(projectId: String): Mono<Boolean> {
        val sql = """
            SELECT EXISTS (
                SELECT 1 FROM project
                WHERE (document ->> 'projectId' = :projectId)
            ) as project_exists
        """.trimIndent()

        return client
            .sql(sql)
            .bind("projectId", projectId)
            .map { row ->
                val projectIsExistsResult = row.get("project_exists", Boolean::class.java)
                log.debug("project_exists $projectIsExistsResult")
                projectIsExistsResult
            }
            .one()
            .map { it!! }
            .doOnNext {
                log.debug("project_exists $it")
            }
    }

    override fun getProjectById(projectId: String): Mono<via.easyflow.modules.project.repository.project.model.ProjectEntity> {
        TODO("Not yet implemented")
    }

    override fun upsertProject(project: via.easyflow.modules.project.repository.project.model.ProjectEntity): Mono<Void> {
        val sql = """
            INSERT INTO project (document) VALUES (:project::jsonb)
             ON CONFLICT ((document ->> 'projectId')) DO UPDATE SET 
             document = project.document || excluded.document;
        """.trimIndent()

        val jsonValue = objectMapper.writeValueAsString(project)
        return client
            .sql(sql)
            .bind("project", jsonValue)

            .then()
    }

    override fun changeProjectOwner(owner: via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity): Mono<Void> {
        val sql = """
            INSERT INTO project_owner (document) VALUES (:owner::jsonb)
                     ON CONFLICT ((document ->> 'projectId')) DO UPDATE SET
                     document = project_owner.document || excluded.document;
        """.trimIndent()

        val jsonValue = objectMapper.writeValueAsString(owner)

        return client
            .sql(sql)
            .bind("owner", jsonValue)
            .then()
    }

    override fun deleteProjectById(id: String): Mono<Void> {
        TODO("Not yet implemented")
    }
}