package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.task.api.contract.`in`.AddTasksIn
import via.easyflow.modules.task.api.contract.`in`.view.CreateTaskView
import via.easyflow.modules.task.api.interaction.service.ITaskService
import via.easyflow.modules.task.api.model.base.TaskModel

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