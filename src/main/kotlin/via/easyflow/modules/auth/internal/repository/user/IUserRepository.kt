package via.easyflow.modules.auth.internal.repository.user

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.auth.model.dto.UserModelDto

interface IUserRepository {
    fun upsert(user: UserModelDto): Mono<Void>
    fun findAll(): Flux<UserModelDto>
    fun findById(id: String): Mono<UserModelDto>
}