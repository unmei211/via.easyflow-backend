package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.GetTasksByUserIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class GetUserTasksInProjectCase(
    private val taskService: ITaskService,
) : TypedUseCase<GetUserTasksInProjectCase.Input, Flux<TaskModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Flux<TaskModel> {
        val userTasks = taskService.getTasksByUser(
            GetTasksByUserIn(
                userId = input.userId,
                projectId = input.projectId,
                limit = input.limit
            )
        )
        return userTasks
    }

    data class Input(
        val projectId: String,
        val userId: String,
        val limit: Long = 10
    )
}