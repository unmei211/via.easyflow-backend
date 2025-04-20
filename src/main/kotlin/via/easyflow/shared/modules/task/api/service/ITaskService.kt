package via.easyflow.shared.modules.task.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.inputs.GetTasksByProjectIn
import via.easyflow.shared.modules.task.api.inputs.GetTasksByUserIn
import via.easyflow.shared.modules.task.model.TaskModel

interface ITaskService {
    fun addTasks(addTasksIn: AddTasksIn): Flux<TaskModel>
    fun getTasksByUser(getTasksByUserIn: GetTasksByUserIn): Flux<TaskModel>
    fun getTasksByProject(getTasksByProjectIn: GetTasksByProjectIn): Flux<TaskModel>
    fun getTaskById(getTaskByIdIn: GetTaskByIdIn): Mono<TaskModel>
    fun changeTask(changeTaskIn: ChangeTaskIn): Mono<TaskModel>
}