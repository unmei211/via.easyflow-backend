package via.easyflow.modules.task.api.interaction.controller.user_project_tasks

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.task.api.contract.`in`.GetTasksByUserIn
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel

@Controller
class UserProjectTasksController(
    private val taskService: ITaskService,
) : IUserProjectTasksController {

    override fun getTasksByUserId(
        userId: String,
        projectId: String,
    ): Mono<ResponseEntity<Flux<TaskModel>>> {
        val result: Mono<ResponseEntity<Flux<TaskModel>>> = Mono.just(
            ResponseEntity.ok(
                taskService.getTasksByUser(
                    GetTasksByUserIn(
                        userId, projectId
                    )
                )
            )
        )
        return result
    }
}