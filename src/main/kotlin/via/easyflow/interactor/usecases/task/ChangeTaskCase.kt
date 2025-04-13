package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.api.inputs.view.ChangeTaskView
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

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