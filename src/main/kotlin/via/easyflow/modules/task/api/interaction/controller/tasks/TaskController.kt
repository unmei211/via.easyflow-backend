package via.easyflow.modules.task.api.interaction.controller.tasks

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.contract.`in`.AddTasksIn
import via.easyflow.modules.task.api.contract.`in`.ChangeTaskIn
import via.easyflow.modules.task.api.contract.`in`.GetTaskByIdIn
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel

@Controller
class TaskController(
    private val taskService: ITaskService
) : ITaskController {
    override fun addTask(taskIn: AddTasksIn): Mono<ResponseEntity<TaskModel>> {
        val tasks: Flux<TaskModel> = taskService.addTasks(taskIn)
        return tasks.single().map { task -> ResponseEntity.ok(task) }
    }

    override fun getTaskById(taskId: String): Mono<ResponseEntity<TaskModel>> {
        return taskService.getTaskById(GetTaskByIdIn(taskId)).map { ResponseEntity.ok(it) }
    }

    override fun patchTaskById(taskId: String, taskIn: ChangeTaskIn): Mono<ResponseEntity<TaskModel>> {
        return taskService.changeTask(taskIn).map { ResponseEntity.ok(it) }
    }
}