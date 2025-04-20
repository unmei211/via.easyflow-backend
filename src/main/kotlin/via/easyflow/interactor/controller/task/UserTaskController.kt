package via.easyflow.interactor.controller.task

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.AddSubtaskRequestBody
import via.easyflow.interactor.controller.task.model.AddTaskRequestBody
import via.easyflow.interactor.controller.task.model.GetTaskInfoRequestBody
import via.easyflow.interactor.controller.task.model.attribute.BaseTasksRequestsAttribute
import via.easyflow.interactor.interactors.task.ITaskInteractor
import via.easyflow.interactor.interactors.task.contract.AddSubtaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.AddUserTasksInProjectInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTaskInfoInteractorInput
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskModel
import via.easyflow.shared.modules.task.model.flow.TaskInfoFlow

@Controller
class UserTaskController(
    private val taskInteractor: ITaskInteractor
) : IUserTaskController {

    override fun getTaskInfo(
        attribute: BaseTasksRequestsAttribute,
        body: GetTaskInfoRequestBody
    ): Mono<ResponseEntity<Mono<TaskInfoFlow>>> {
        val taskInfo = taskInteractor.getTaskInfo(
            GetTaskInfoInteractorInput(
                userId = body.userId,
                taskId = body.taskId
            ),
        )

        return Mono.just(ResponseEntity.ok(taskInfo))
    }

    override fun addSubtask(
        body: AddSubtaskRequestBody,
        attribute: BaseTasksRequestsAttribute
    ): Mono<ResponseEntity<Mono<SubtaskModel>>> {
        val subtask = taskInteractor.addSubtask(
            AddSubtaskInteractorInput(
                ownerId = attribute.userId,
                taskId = attribute.taskId!!,
                assigneeId = body.assigneeId,
                description = body.description,
                title = body.title,
                projectId = attribute.projectId,
            )
        )
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(subtask))
    }

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