package via.easyflow.interactor.controller.task

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.task.model.ChangeTaskRequestBody
import via.easyflow.interactor.controller.task.model.TeamTaskAttribute
import via.easyflow.interactor.interactors.task.ITaskInteractor
import via.easyflow.interactor.interactors.task.contract.ChangeTaskInteractorInput
import via.easyflow.modules.task.api.model.base.TaskModel

@Controller
class TeamTaskController(
    private val taskInteractor: ITaskInteractor,
) : ITeamTaskController {
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