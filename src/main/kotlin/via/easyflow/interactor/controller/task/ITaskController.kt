package via.easyflow.interactor.controller.task

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.modules.task.api.model.base.TaskModel

@RestController
@RequestMapping("/interactor/tasks", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface ITaskController {
    @PostMapping()
    fun addTask(@RequestBody input: AddTaskInteractorInput): Mono<ResponseEntity<Mono<TaskModel>>>
}