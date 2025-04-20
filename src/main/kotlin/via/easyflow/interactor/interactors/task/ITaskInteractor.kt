package via.easyflow.interactor.interactors.task

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.contract.AddSubtaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskCommentInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTaskInfoInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTasksByProjectInteractorInput
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel
import via.easyflow.shared.modules.task.model.flow.TaskInfoFlow

interface ITaskInteractor {
    fun getTaskInfo(input: GetTaskInfoInteractorInput): Mono<TaskInfoFlow>
    fun getTasksByProject(input: GetTasksByProjectInteractorInput): Flux<TaskModel>
    fun addTask(input: AddTaskInteractorInput): Mono<TaskModel>
    fun getUserTasksInProject(input: AddUserTasksInProjectInteractorInput): Flux<TaskModel>
    fun changeTask(input: ChangeTaskInteractorInput): Mono<TaskModel>
    fun addTaskComment(input: AddTaskCommentInteractorInput): Mono<TaskCommentModel>
    fun addSubtask(input: AddSubtaskInteractorInput): Mono<SubtaskModel>
}