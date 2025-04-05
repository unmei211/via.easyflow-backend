package via.easyflow.modules.task.api.interaction.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.contract.`in`.AddTasksIn
import via.easyflow.modules.task.api.model.base.TaskModel

@RestController
@RequestMapping("/tasks")
interface ITaskController {
    @PostMapping()
    fun addTask(@RequestBody taskIn: AddTasksIn): Mono<ResponseEntity<TaskModel>>

    @GetMapping("/{taskId}")
    fun getTaskById(@PathVariable taskId: String): Mono<ResponseEntity<TaskModel>>

}