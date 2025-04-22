package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.subtask.AddSubtaskIn
import via.easyflow.shared.modules.task.api.service.ISubtaskService
import via.easyflow.shared.modules.task.model.SubtaskModel

@Component
class AddSubtaskCase(
    private val subtaskService: ISubtaskService,
) : UseCase<AddSubtaskCase.Input, Mono<SubtaskModel>> {
    override fun invoke(input: Input): Mono<SubtaskModel> {
        val subtask = subtaskService.addSubtask(
            AddSubtaskIn(
                ownerId = input.ownerId,
                taskId = input.taskId,
                assigneeId = input.assigneeId,
                description = input.description,
                title = input.title,
            )
        )
        return subtask
    }

    data class Input(
        val ownerId: String,
        val taskId: String,
        val assigneeId: String? = null,
        val description: String? = null,
        val title: String
    )
}