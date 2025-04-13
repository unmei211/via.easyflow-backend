package via.easyflow.modules.project.internal.repository.member

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.core.tools.database.query.builder.IQueryBuilder
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.param.filler.IQueryParamMapper
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.repository.member.contract.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.internal.repository.member.contract.enquiry.GrantRolesToMemberEnquiry

@Repository
class MemberRepository(
    private val client: DatabaseClient,
    private val objectMapper: ObjectMapper,
    private val connectionFactory: ConnectionFactory,
    private val jsonRIO: ReactiveJsonIO,
    private val queryBuilder: IQueryBuilder,
    private val paramMapper: IQueryParamMapper<DatabaseClient.GenericExecuteSpec, Mono<DatabaseClient.GenericExecuteSpec>>
) : IMemberRepository {
    private val log = logger()
    override fun searchByFilter(filters: List<IQueryFilter>): Flux<ProjectMemberEntity> {
        val sql = """
            SELECT document FROM project_member WHERE ${queryBuilder.build(filters)}
        """.trimIndent()

        val specs: DatabaseClient.GenericExecuteSpec = client.sql(sql)

        val specsMono: Mono<DatabaseClient.GenericExecuteSpec> = paramMapper.fillByFilters(
            filters,
            specs
        )

        val rows: Flux<MutableMap<String, Any>> = specsMono.flatMapMany {
            it.fetch().all()
        }


        return rows.concatMap { row ->
            jsonRIO.fromJson(row["document"] as Json, ProjectMemberEntity::class)
        }.switchIfEmpty(Mono.error(NotFoundException("Not found")))
    }

    override fun existsMemberByFilters(filters: List<IQueryFilter>): Mono<Boolean> {
        val sql = """
            SELECT EXISTS(SELECT 1 FROM project_member WHERE ${queryBuilder.build(filters)}) as exists;    
        """.trimIndent()

        val specs = paramMapper.fillByFilters(
            filters,
            client.sql(sql)
        )

        return specs.flatMap {
            it
                .fetch()
                .one()
                .map { row -> row["exists"] as Boolean }
        }
    }

    override fun connectMembersToProject(connectMembersEnquiry: ConnectMembersEnquiry): Mono<ProjectMemberEntity> {
        val member: Mono<ProjectMemberEntity> = connectMembersEnquiry.projectMemberEntity

        val sql = """
        WITH exist_project as (
            SELECT 1 WHERE EXISTS(
                SELECT 1 from project
                WHERE document ->> 'projectId' = :projectId
            )
        )
        INSERT INTO project_member (document) 
            SELECT :projectMemberDocument::jsonb
                FROM exist_project
        RETURNING document
    """.trimIndent()

        return member
            .doOnNext {
                log.debug("Current member to connectProject: {}", it)
            }
            .flatMap { Mono.zip(jsonRIO.toJson(it), Mono.just(it)) }
            .flatMap { data ->
                log.debug("Data to insert: {}", data)
                client
                    .sql(sql)
                    .bind("projectMemberDocument", data.t1)
                    .bind("projectId", data.t2.projectId)
                    .map { row ->
                        log.debug("row: {}", row)
                        row.get("document", String::class.java)
                    }
                    .one()
                    .doOnError {
                        log.debug("Error when try insert project member")
                    }
                    .doOnNext {
                        log.debug("wtf + $it")
                    }
                    .flatMap { jsonRIO.fromJson(it!!, ProjectMemberEntity::class) }
            }
    }

    override fun grantRolesToMember(grantEnquiry: GrantRolesToMemberEnquiry): Flux<ProjectMemberRoleEntity> {
        val sql = """
            WITH exist_projectMember AS (
                SELECT 1 WHERE EXISTS
                 (SELECT 1 FROM project_member
                    WHERE (document ->> 'projectMemberId' = :projectMemberId))
            ),
            exist_projetRole AS (
                SELECT 1 WHERE EXISTS
                 (SELECT 1 FROM project_role
                    WHERE (document ->> 'projectRoleId' = :projectRoleId))
            )
            INSERT INTO project_member_role (document) 
            SELECT :document::jsonb
            FROM exist_projectMember, exist_projetRole
            ON CONFLICT ((document ->> 'projectMemberRoleId'))
            DO UPDATE SET document = EXCLUDED.document
        """.trimIndent()

        return client.inConnection { conn ->
            val statement = conn.createStatement(sql)

            grantEnquiry.memberRoleEntities
                .flatMap { Mono.zip(jsonRIO.toJson(it), Mono.just(it)) }
                .doOnNext { jsonAndRaw ->
                    statement
                        .bind("document", jsonAndRaw.t1)
                        .bind("projectMemberId", jsonAndRaw.t2.projectMemberId)
                        .bind("projectRoleId", jsonAndRaw.t2.projectRoleId)
                        .add()
                }
                .then(Mono.from(statement.execute()))
        }.thenMany(grantEnquiry.memberRoleEntities)
    }
}