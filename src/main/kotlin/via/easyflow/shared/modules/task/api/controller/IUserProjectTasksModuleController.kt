package via.easyflow.shared.modules.task.api.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.model.TaskModel

@RestController
@RequestMapping("/module/user/{userId}/project/{projectId}/tasks", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IUserProjectTasksModuleController {

    @GetMapping()
    fun getTasksByUserId(
        @PathVariable userId: String,
        @PathVariable projectId: String,
        @RequestParam(required = false) limit: Long?,
    ): Mono<ResponseEntity<Flux<TaskModel>>>
}