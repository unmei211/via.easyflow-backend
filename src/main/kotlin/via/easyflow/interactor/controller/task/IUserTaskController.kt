package via.easyflow.interactor.controller.task

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.AddSubtaskRequestBody
import via.easyflow.interactor.controller.task.model.AddTaskRequestBody
import via.easyflow.interactor.controller.task.model.GetTaskInfoRequestBody
import via.easyflow.interactor.controller.task.model.attribute.BaseTasksRequestsAttribute
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskModel
import via.easyflow.shared.modules.task.model.flow.TaskInfoFlow

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

    @PostMapping("/{taskId}/subtask")
    fun addSubtask(
        @RequestBody body: AddSubtaskRequestBody,
        @ModelAttribute attribute: BaseTasksRequestsAttribute
    ): Mono<ResponseEntity<Mono<SubtaskModel>>>

    @GetMapping("/info")
    fun getTaskInfo(
        @ModelAttribute attribute: BaseTasksRequestsAttribute,
        @RequestBody body: GetTaskInfoRequestBody
    ): Mono<ResponseEntity<Mono<TaskInfoFlow>>>
}