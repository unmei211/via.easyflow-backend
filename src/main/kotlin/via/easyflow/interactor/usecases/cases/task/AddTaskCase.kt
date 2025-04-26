package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.exceptions.exception.NotFoundException
import via.easyflow.shared.modules.task.api.inputs.AddTasksIn
import via.easyflow.shared.modules.task.api.inputs.view.CreateTaskView
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel
import kotlin.reflect.KClass

@Component
@Case(scope = CaseScope.TASK)
class AddTaskCase(
    private val taskService: ITaskService,
) : TypedUseCase<AddTaskCase.Input, Mono<TaskModel>> {
    override fun invoke(input: Input): Mono<TaskModel> {
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

    override fun getInputType(): KClass<Input> = Input::class

    data class Input(
        val taskName: String,
        val taskDescription: String,
        val userId: String,
        val projectId: String
    )
}
