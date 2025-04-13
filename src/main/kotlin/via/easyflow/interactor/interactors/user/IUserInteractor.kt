package via.easyflow.interactor.interactors.user

import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.shared.modules.auth.model.user.UserModel

interface IUserInteractor {
    fun upsertUser(input: UpsertUserInteractorInput): Mono<UserModel>
}