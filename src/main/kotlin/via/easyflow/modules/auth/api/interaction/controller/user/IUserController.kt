package via.easyflow.modules.auth.api.interaction.controller.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.contract.`in`.user.UpsertUserIn
import via.easyflow.modules.auth.api.contract.out.user.ExistsUserOut
import via.easyflow.modules.auth.api.contract.out.user.UpsertUserOut

@RestController
@RequestMapping("/auth/user")
interface IUserController {
    @PostMapping("/upsert")
    fun upsert(upsertIn: UpsertUserIn): Mono<ResponseEntity<UpsertUserOut>>

    @GetMapping("/{userId}/exists")
    fun existsUser(@ModelAttribute existsIn: ExistsUserIn): Mono<ResponseEntity<ExistsUserOut>>
}