package via.easyflow.interactor.controller.user

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.IUserInteractor
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.modules.auth.api.model.user.UserModel

@Controller
class UserController(
    private val userInteractor: IUserInteractor
) : IUserController {
    override fun upsert(upsertUserInteractorInput: UpsertUserInteractorInput):
            Mono<ResponseEntity<Mono<UserModel>>> {
        return Mono.just(ResponseEntity.ok(userInteractor.upsertUser(input = upsertUserInteractorInput)))
    }
}