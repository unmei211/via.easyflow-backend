package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.task.api.contract.`in`.GetTasksByUserIn
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel

@Component
class GetUserTasksInProjectCase(
    private val taskService: ITaskService,
) : UseCase<GetUserTasksInProjectCaseInput, Flux<TaskModel>> {
    override fun invoke(input: GetUserTasksInProjectCaseInput): Flux<TaskModel> {
        val userTasks = taskService.getTasksByUser(
            GetTasksByUserIn(
                userId = input.userId,
                projectId = input.projectId,
                limit = input.limit
            )
        )
        return userTasks
    }
}

data class GetUserTasksInProjectCaseInput(
    val projectId: String,
    val userId: String,
    val limit: Long = 10
)