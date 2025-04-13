package via.easyflow.interactor.interactors.task

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.modules.task.api.model.base.TaskModel

interface ITaskInteractor {
    fun addTask(input: AddTaskInteractorInput): Mono<TaskModel>
    fun getUserTasksInProject(input: AddUserTasksInProjectInteractorInput): Flux<TaskModel>
    fun changeTask(input: ChangeTaskInteractorInput): Mono<TaskModel>
}