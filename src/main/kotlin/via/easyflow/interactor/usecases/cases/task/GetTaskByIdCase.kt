package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.GetTaskByIdIn
import via.easyflow.shared.modules.task.api.service.ITaskService
import via.easyflow.shared.modules.task.model.TaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class GetTaskByIdCase(
    private val taskService: ITaskService,
) : TypedUseCase<GetTaskByIdCase.Input, Mono<TaskModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<TaskModel> {
        return taskService.getTaskById(GetTaskByIdIn(input.taskId))
    }

    data class Input(val taskId: String)
}