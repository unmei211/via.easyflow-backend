package via.easyflow.interactor.controller.task

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.task.ITaskInteractor
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.modules.task.api.model.base.TaskModel

@Controller
class TaskController(
    private val taskInteractor: ITaskInteractor
) : ITaskController {
    override fun addTask(input: AddTaskInteractorInput): Mono<ResponseEntity<Mono<TaskModel>>> {
        val addedTask = taskInteractor.addTask(input)
        return Mono.just(ResponseEntity.ok(addedTask))
    }
}