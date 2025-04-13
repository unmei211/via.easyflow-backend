package via.easyflow.modules.task.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.api.controller.ITaskModuleController
import via.easyflow.shared.modules.task.model.TaskModel

@Controller
class TaskModuleController(
    private val taskService: ITaskService
) : ITaskModuleController {
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