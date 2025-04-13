package via.easyflow.modules.task.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.GetTasksByUserIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.api.controller.IUserProjectTasksModuleController
import via.easyflow.shared.modules.task.model.TaskModel

@Controller
class UserProjectTasksModuleController(
    private val taskService: ITaskService,
) : IUserProjectTasksModuleController {

    override fun getTasksByUserId(
        userId: String,
        projectId: String,
        limit: Long?,
    ): Mono<ResponseEntity<Flux<TaskModel>>> {
        val result: Mono<ResponseEntity<Flux<TaskModel>>> = Mono.just(
            ResponseEntity.ok(
                taskService.getTasksByUser(
                    GetTasksByUserIn(
                        userId, projectId, limit
                    )
                )
            )
        )
        return result
    }
}