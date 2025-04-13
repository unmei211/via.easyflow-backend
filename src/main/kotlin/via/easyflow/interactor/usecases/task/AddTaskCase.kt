package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.view.CreateTaskView
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel

@Component
class AddTaskCase(
    private val taskService: ITaskService,
) : UseCase<AddTaskCaseInput, Mono<TaskModel>> {
    override fun invoke(input: AddTaskCaseInput): Mono<TaskModel> {
        return taskService.addTasks(
            addTasksIn = AddTasksIn(
                tasks = listOf(
                    CreateTaskView(
                        name = input.taskName,
                        description = input.taskDescription,

                        )
                ),
                projectId = input.projectId,
                ownerId = input.userId,
            )
        )
            .singleOrEmpty()
            .switchIfEmpty(Mono.error(NotFoundException(input.userId)))
    }
}

data class AddTaskCaseInput(
    val taskName: String,
    val taskDescription: String,
    val userId: String,
    val projectId: String
)