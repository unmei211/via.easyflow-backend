package via.easyflow.modules.auth.internal.repository.user

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.datasource.ClientFactory
import via.easyflow.core.datasource.IClientFactory
import via.easyflow.modules.auth.model.dto.UserModelDto

class UserRepository(
    private val client: DatabaseClient,
) : IUserRepository {
    override fun upsert(user: UserModelDto): Mono<Void> {
        client.sql("SELECT * FROM users WHERE (document ->> 'id' = '${user.id}')")
            .fetch()
            .one()
            .map { row -> row["documtn"]}
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<UserModelDto> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Mono<UserModelDto> {
        TODO("Not yet implemented")
    }
}