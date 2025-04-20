package via.easyflow.interactor.controller.task

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.AddTaskRequestBody
import via.easyflow.interactor.controller.task.model.attribute.BaseTasksRequestsAttribute
import via.easyflow.interactor.interactors.task.ITaskInteractor
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.shared.modules.task.model.TaskModel

@Controller
class UserTaskController(
    private val taskInteractor: ITaskInteractor
) : IUserTaskController {
    override fun addTask(
        attribute: BaseTasksRequestsAttribute,
        body: AddTaskRequestBody
    ): Mono<ResponseEntity<Mono<TaskModel>>> {
        val input = AddTaskInteractorInput(
            taskName = body.taskName,
            taskDescription = body.taskDescription,
            userId = attribute.userId,
            projectId = attribute.projectId,
        )
        return Mono.just(ResponseEntity.ok(taskInteractor.addTask(input)))
    }

    override fun getUserTasksInProject(attribute: BaseTasksRequestsAttribute): Mono<ResponseEntity<Flux<TaskModel>>> {
        return Mono.just(
            ResponseEntity.ok(
                taskInteractor.getUserTasksInProject(
                    AddUserTasksInProjectInteractorInput(
                        userId = attribute.userId,
                        projectId = attribute.projectId
                    )
                )
            )
        )
    }
}