package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.GetTasksByUserIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

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