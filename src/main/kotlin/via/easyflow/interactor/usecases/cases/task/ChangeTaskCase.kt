package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.ChangeTaskIn
import via.easyflow.shared.modules.task.api.inputs.view.ChangeTaskView
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class ChangeTaskCase(
    private val taskService: ITaskService,
) : TypedUseCase<ChangeTaskCase.Input, Mono<TaskModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<TaskModel> {
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

    data class Input(
        val taskName: String,
        val taskDescription: String,
        val userId: String,
        val projectId: String,
        val taskVersion: String,
        val taskStatus: String,
        val taskId: String
    )
}
