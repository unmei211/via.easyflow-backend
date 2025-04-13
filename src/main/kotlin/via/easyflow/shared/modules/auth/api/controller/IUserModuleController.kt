package via.easyflow.shared.modules.auth.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.api.outputs.user.ExistsUserOut
import via.easyflow.shared.modules.auth.api.outputs.user.UpsertUserOut

@RestController
@RequestMapping("/module/auth/user")
interface IUserModuleController {
    @PostMapping("/upsert")
    fun upsert(@RequestBody upsertIn: UpsertUserIn): Mono<ResponseEntity<UpsertUserOut>>

    @GetMapping("/{userId}/exists")
    fun existsUser(@ModelAttribute existsIn: ExistsUserIn): Mono<ResponseEntity<ExistsUserOut>>
}