package via.easyflow.modules.task.api.interaction.controller.user_project_tasks

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.model.base.TaskModel

@RestController
@RequestMapping("/user/{userId}/project/{projectId}/tasks", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IUserProjectTasksController {

    @GetMapping()
    fun getTasksByUserId(
        @PathVariable userId: String,
        @PathVariable projectId: String,
    ): Mono<ResponseEntity<Flux<TaskModel>>>
}