package via.easyflow.interactor.controller.task

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.ChangeTaskRequestBody
import via.easyflow.interactor.controller.task.model.PushCommentRequestBody
import via.easyflow.interactor.controller.task.model.attribute.TeamTaskAttribute
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel

@RestController
@RequestMapping(
    "/interactor/projects/{projectId}/tasks",
    produces = [MediaType.APPLICATION_NDJSON_VALUE]
)
interface ITeamTaskController {
    @GetMapping
    fun getTasksByProject(@ModelAttribute attribute: TeamTaskAttribute): Flux<TaskModel>

    @PatchMapping("/{taskId}")
    fun changeTask(
        @ModelAttribute attribute: TeamTaskAttribute,
        @RequestBody body: ChangeTaskRequestBody
    ): Mono<ResponseEntity<Mono<TaskModel>>>

    @PostMapping("/{taskId}")
    fun pushComment(
        @ModelAttribute attribute: TeamTaskAttribute,
        @RequestBody body: PushCommentRequestBody
    ): Mono<ResponseEntity<Mono<TaskCommentModel>>>
}