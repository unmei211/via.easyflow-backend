package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.subtask.AddSubtaskIn
import via.easyflow.shared.modules.task.api.service.ISubtaskService
import via.easyflow.shared.modules.task.model.SubtaskModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class AddSubtaskCase(
    private val subtaskService: ISubtaskService,
) : TypedUseCase<AddSubtaskCase.Input, Mono<SubtaskModel>> {
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

    override fun getInputType(): KClass<Input> {
        return Input::class
    }
}