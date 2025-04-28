package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.subtask.GetSubtasksByTaskIdInput
import via.easyflow.shared.modules.task.api.service.ISubtaskService
import via.easyflow.shared.modules.task.model.SubtaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class GetSubtaskCase(
    private val subtaskService: ISubtaskService,
) : TypedUseCase<GetSubtaskCase.Input, Flux<SubtaskModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Flux<SubtaskModel> {
        return subtaskService.getSubtasksByTaskId(
            input = GetSubtasksByTaskIdInput(
                input.taskId
            )
        )
    }

    data class Input(val taskId: String)
}