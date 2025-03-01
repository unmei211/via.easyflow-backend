package via.easyflow.modules.auth.internal.repository.user

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.R2dbcMapper
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import via.easyflow.modules.auth.api.model.user.UserModel
import via.easyflow.modules.auth.internal.entity.UserEntity
import via.easyflow.modules.auth.internal.repository.user.contract.enquiry.UpsertUserEnquiry
import via.easyflow.modules.auth.internal.repository.user.contract.query.FindAllUsersQuery
import via.easyflow.modules.auth.internal.repository.user.contract.query.FindByIdUserQuery


@Repository
class UserRepository(
    private val dbClient: DatabaseClient,
    private val jsonRIO: ReactiveJsonIO,
    private val r2dbcMapper: R2dbcMapper
) : IUserRepository {
    override fun upsert(enquiry: UpsertUserEnquiry): Mono<UserEntity> {
        val sql = """
            INSERT INTO users (document) VALUES (:user)
            ON CONFLICT ((document ->> 'userId')) 
                DO UPDATE SET document = EXCLUDED.document || :document
            RETURNING document
        """.trimIndent()
        return dbClient
            .sql(sql)
            .bind("user", enquiry)
            .flatMap { r2dbcMapper.flatMap(it, UserEntity::class) }
            .next()
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