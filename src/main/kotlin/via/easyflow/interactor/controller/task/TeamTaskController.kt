package via.easyflow.interactor.controller.task

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.ChangeTaskRequestBody
import via.easyflow.interactor.controller.task.model.PushCommentRequestBody
import via.easyflow.interactor.controller.task.model.attribute.TeamTaskAttribute
import via.easyflow.interactor.interactors.task.ITaskInteractor
import via.easyflow.interactor.interactors.task.contract.AddTaskCommentInteractorInput
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.interactor.interactors.task.contract.GetTasksByProjectInteractorInput
import via.easyflow.shared.modules.task.model.TaskCommentModel
import via.easyflow.shared.modules.task.model.TaskModel

@Controller
class TeamTaskController(
    private val taskInteractor: ITaskInteractor,
) : ITeamTaskController {
    override fun getTasksByProject(attribute: TeamTaskAttribute): Flux<TaskModel> {
        val projectTasks: Flux<TaskModel> = taskInteractor.getTasksByProject(
            GetTasksByProjectInteractorInput(
                projectId = attribute.projectId
            )
        )
        return projectTasks
    }

    override fun pushComment(
        attribute: TeamTaskAttribute,
        body: PushCommentRequestBody
    ): Mono<ResponseEntity<Mono<TaskCommentModel>>> {
        val taskComment = taskInteractor.addTaskComment(
            AddTaskCommentInteractorInput(
                comment = body.comment,
                taskId = attribute.taskId!!,
                userId = body.userId
            )
        )

        return Mono.just(ResponseEntity.ok(taskComment))
    }

    override fun changeTask(
        attribute: TeamTaskAttribute,
        body: ChangeTaskRequestBody
    ): Mono<ResponseEntity<Mono<TaskModel>>> {
        return Mono.just(
            ResponseEntity.ok(
                taskInteractor.changeTask(
                    ChangeTaskInteractorInput(
                        version = body.version,
                        description = body.description,
                        status = body.status,
                        name = body.name,
                        changerUserId = body.userId,
                        projectId = attribute.projectId,
                        taskId = attribute.taskId!!
                    ),
                )
            )
        )
    }
}