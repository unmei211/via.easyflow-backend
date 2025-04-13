package via.easyflow.interactor.controller.task

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.AddTaskRequestBody
import via.easyflow.interactor.controller.task.model.BaseTasksRequestsAttribute
import via.easyflow.shared.modules.task.model.TaskModel

@RestController
@RequestMapping(
    "/interactor/projects/{projectId}/users/{userId}/tasks",
    produces = [MediaType.APPLICATION_NDJSON_VALUE]
)
interface IUserTaskController {
    @PostMapping()
    fun addTask(
        @ModelAttribute attribute: BaseTasksRequestsAttribute,
        @RequestBody body: AddTaskRequestBody
    ): Mono<ResponseEntity<Mono<TaskModel>>>

    @GetMapping()
    fun getUserTasksInProject(
        @ModelAttribute attribute: BaseTasksRequestsAttribute
    ): Mono<ResponseEntity<Flux<TaskModel>>>
}