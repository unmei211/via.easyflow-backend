package via.easyflow.modules.auth.controller.user

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.shared.modules.auth.api.inputs.user.ExistsUserIn
import via.easyflow.shared.modules.auth.api.inputs.user.UpsertUserIn
import via.easyflow.shared.modules.auth.api.outputs.user.ExistsUserOut
import via.easyflow.shared.modules.auth.api.outputs.user.UpsertUserOut
import via.easyflow.shared.modules.auth.api.controller.IUserModuleController
import via.easyflow.shared.modules.auth.api.service.IUserService

@Controller
class UserModuleController(
    private val userService: IUserService,
) : IUserModuleController {
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