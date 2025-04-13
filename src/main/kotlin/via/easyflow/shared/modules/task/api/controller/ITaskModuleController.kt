package via.easyflow.shared.modules.task.api.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.model.TaskModel

@RestController
@RequestMapping("/module/tasks", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface ITaskModuleController {
    @PostMapping()
    fun addTask(@RequestBody taskIn: AddTasksIn): Mono<ResponseEntity<TaskModel>>

    @GetMapping("/{taskId}")
    fun getTaskById(@PathVariable taskId: String): Mono<ResponseEntity<TaskModel>>

    @PatchMapping("/{taskId}")
    fun patchTaskById(@PathVariable taskId: String, @RequestBody taskIn: ChangeTaskIn): Mono<ResponseEntity<TaskModel>>
}