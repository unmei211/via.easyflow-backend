package via.easyflow.user.web

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import via.easyflow.user.model.dto.UserModelDto
import via.easyflow.user.model.internal.UserModel

interface IUserController {
    fun getUserById(id: String): Mono<ResponseEntity<UserModelDto>>
}