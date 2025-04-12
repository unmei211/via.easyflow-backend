package via.easyflow.interactor.interactors.task

import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.modules.task.api.model.base.TaskModel

interface ITaskInteractor {
    fun addTask(input: AddTaskInteractorInput): Mono<TaskModel>
}