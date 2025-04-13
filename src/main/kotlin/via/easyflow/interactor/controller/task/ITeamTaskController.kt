package via.easyflow.interactor.controller.task

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.ChangeTaskRequestBody
import via.easyflow.interactor.controller.task.model.TeamTaskAttribute
import via.easyflow.modules.task.api.model.base.TaskModel

@RestController
@RequestMapping(
    "/interactor/projects/{projectId}/tasks",
    produces = [MediaType.APPLICATION_NDJSON_VALUE]
)
interface ITeamTaskController {
    @PatchMapping("/{taskId}")
    fun changeTask(
        @ModelAttribute attribute: TeamTaskAttribute,
        @RequestBody body: ChangeTaskRequestBody
    ): Mono<ResponseEntity<Mono<TaskModel>>>

}