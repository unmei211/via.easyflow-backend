package via.easyflow.modules.auth.api.interaction.controller.user

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.contract.`in`.user.UpsertUserIn
import via.easyflow.modules.auth.api.contract.out.user.ExistsUserOut
import via.easyflow.modules.auth.api.contract.out.user.UpsertUserOut
import via.easyflow.modules.auth.api.interaction.service.user.IUserService

@Controller
class UserController(
    private val userService: IUserService,
) : IUserController {
    private val log = logger()

    override fun upsert(@RequestBody upsertIn: UpsertUserIn): Mono<ResponseEntity<UpsertUserOut>> {
        return userService
            .upsertUser(upsertIn)
            .map {
                ResponseEntity.ok(
                    UpsertUserOut(it)
                )
            }
    }

    override fun existsUser(existsIn: ExistsUserIn): Mono<ResponseEntity<ExistsUserOut>> {
        return userService
            .existsUser(existsIn)
            .flatMap { Mono.just(ResponseEntity.ok(ExistsUserOut(it))) }
    }
}