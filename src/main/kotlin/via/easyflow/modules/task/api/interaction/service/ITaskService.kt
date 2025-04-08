package via.easyflow.modules.task.api.interaction.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.contract.`in`.*
import via.easyflow.modules.task.api.model.base.TaskModel

interface ITaskService {
    fun addTasks(addTasksIn: AddTasksIn): Flux<TaskModel>
    fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): Flux<TaskModel>
    fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn)
    fun getTaskById(getTaskByIdIn: GetTaskByIdIn): Mono<TaskModel>
    fun changeTask(changeTaskIn: ChangeTaskIn): Mono<TaskModel>
}