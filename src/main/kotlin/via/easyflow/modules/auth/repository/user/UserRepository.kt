package via.easyflow.modules.auth.repository.user

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.mapper.R2dbcMapper
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.auth.repository.user.model.UserEntity
import via.easyflow.modules.auth.repository.user.contract.enquiry.UpsertUserEnquiry
import via.easyflow.modules.auth.repository.user.contract.query.FindAllUsersQuery
import via.easyflow.modules.auth.repository.user.contract.query.FindByIdUserQuery


@Repository
class UserRepository(
    private val dbClient: DatabaseClient,
    private val r2dbcMapper: R2dbcMapper,
    private val jsonRIO: ReactiveJsonIO
) : IUserRepository {
    private val log = logger()

    override fun upsert(enquiry: UpsertUserEnquiry): Mono<UserEntity> {
        val sql = """
            INSERT INTO users (document) VALUES (:user::jsonb)
            ON CONFLICT ((document ->> 'userId')) 
                DO UPDATE SET document = (users.document || EXCLUDED.document)
            RETURNING document
        """.trimIndent()

        return jsonRIO
            .toJson(enquiry.user)
            .flatMap { json ->
                dbClient
                    .sql(sql)
                    .bind("user", json)
                    .flatMap {
                        log.debug("Successfully updated user {}", json)
                        r2dbcMapper.flatMap(it, UserEntity::class)
                    }
                    .next()
            }
    }

    override fun findAll(query: FindAllUsersQuery?): Flux<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun findById(query: FindByIdUserQuery): Mono<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun existsById(query: FindByIdUserQuery): Mono<Boolean> {
        val sql = """
            SELECT EXISTS(
                SELECT 1 FROM users
                WHERE (document ->> 'userId' = :userId)
            ) AS is_exists  
        """.trimIndent()
        return dbClient
            .sql(sql)
            .bind("userId", query.userId)
            .map { row -> row.get("is_exists", Boolean::class.java)!! }
            .one()
    }
}