package via.easyflow.interactor.controller.user

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.shared.modules.auth.model.user.UserModel

@RestController
@RequestMapping("/interactor/auth/user", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IUserController {
    @PostMapping("/upsert")
    fun upsert(@RequestBody upsertUserInteractorInput: UpsertUserInteractorInput): Mono<ResponseEntity<Mono<UserModel>>>
}