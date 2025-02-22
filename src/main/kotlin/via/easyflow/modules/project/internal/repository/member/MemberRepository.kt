package via.easyflow.modules.project.internal.repository.member

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.repository.member.model.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.internal.repository.member.model.enquiry.GrantRolesToMemberEnquiry

@Repository
class MemberRepository(
    private val client: DatabaseClient,
    private val objectMapper: ObjectMapper,
    private val connectionFactory: ConnectionFactory,
    private val jsonRIO: ReactiveJsonIO
) : IMemberRepository {
    override fun connectMembersToProject(connectMembersEnquiry: ConnectMembersEnquiry): Flux<ProjectMemberEntity> {
        val members = connectMembersEnquiry.projectMemberEntity

        val sql = """
            WITH exist_project as (
                SELECT 1 WHERE EXISTS
                    (
                        SELECT 1 from project
                            WHERE document ->> 'projectId' = :projectId
                    )
            )
            INSERT INTO project_member (document) VALUES (:projectMember::jsonb)
        """.trimIndent()

        return client.inConnection { conn ->
            val statement = Mono.just(conn.createStatement(sql))

            val memberJsonFlux = members.concatMap { member -> jsonRIO.toJson(member) }.zipWith(members)

            val bindedAccept = memberJsonFlux.flatMap { member ->
                Mono.from(statement).map { st ->
                    st
                        .bind("document", member.t1)
                        .bind("projectId", member.t2.projectId)
                        .add()
                }
            }.then()

            Mono.zip(statement, bindedAccept).flatMap { Mono.from(it.t1.execute()) }.then()
        }.flatMapMany { members }
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