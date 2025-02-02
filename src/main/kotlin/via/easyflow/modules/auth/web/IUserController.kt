package via.easyflow.modules.auth.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.modules.auth.model.dto.UserModelDto

@RestController
@RequestMapping("/users")
interface IUserController {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): Mono<ResponseEntity<UserModelDto>>
}