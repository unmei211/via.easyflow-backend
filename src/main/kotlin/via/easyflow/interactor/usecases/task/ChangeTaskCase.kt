package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.task.api.contract.`in`.ChangeTaskIn
import via.easyflow.modules.task.api.contract.`in`.view.ChangeTaskView
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel

@Component
class ChangeTaskCase(
    private val taskService: ITaskService,
) : UseCase<ChangeTaskCaseInput, Mono<TaskModel>> {
    override fun invoke(input: ChangeTaskCaseInput): Mono<TaskModel> {
        return taskService.changeTask(
            ChangeTaskIn(
                task = ChangeTaskView(
                    description = input.taskDescription,
                    status = input.taskStatus,
                    name = input.taskName,
                ),
                taskId = input.taskId,
                changerId = input.userId,
                version = input.taskVersion
            )
        )
    }
}

data class ChangeTaskCaseInput(
    val taskName: String,
    val taskDescription: String,
    val userId: String,
    val projectId: String,
    val taskVersion: String,
    val taskStatus: String,
    val taskId: String
)