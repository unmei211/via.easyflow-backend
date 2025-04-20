package via.easyflow.interactor.usecases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.subtask.GetSubtasksByTaskIdInput
import via.easyflow.shared.modules.task.api.service.ISubtaskService
import via.easyflow.shared.modules.task.model.SubtaskModel

@Component
class GetSubtaskCase(
    private val subtaskService: ISubtaskService,
) : UseCase<GetSubtaskCase.Input, Flux<SubtaskModel>> {
    override fun invoke(input: Input): Flux<SubtaskModel> {
        return subtaskService.getSubtasksByTaskId(
            input = GetSubtasksByTaskIdInput(
                input.taskId
            )
        )
    }

    data class Input(val taskId: String)
}