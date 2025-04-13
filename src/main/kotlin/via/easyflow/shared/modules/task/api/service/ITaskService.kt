package via.easyflow.shared.modules.task.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.*
import via.easyflow.shared.modules.task.model.TaskModel

interface ITaskService {
    fun addTasks(addTasksIn: AddTasksIn): Flux<TaskModel>
    fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): Flux<TaskModel>
    fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn)
    fun getTaskById(getTaskByIdIn: GetTaskByIdIn): Mono<TaskModel>
    fun changeTask(changeTaskIn: ChangeTaskIn): Mono<TaskModel>
}