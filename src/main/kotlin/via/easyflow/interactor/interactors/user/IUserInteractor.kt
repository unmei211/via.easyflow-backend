package via.easyflow.interactor.interactors.user

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.user.contract.UpsertUserInteractorInput
import via.easyflow.modules.auth.api.model.user.UserModel
import via.easyflow.modules.task.api.model.base.TaskModel

interface IUserInteractor {
    fun upsertUser(input: UpsertUserInteractorInput): Mono<UserModel>
}