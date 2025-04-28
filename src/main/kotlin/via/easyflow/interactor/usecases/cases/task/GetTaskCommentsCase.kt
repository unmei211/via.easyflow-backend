package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.task.api.inputs.GetTaskCommentsIn
import via.easyflow.shared.modules.task.api.service.ITaskCommentService
import via.easyflow.shared.modules.task.model.TaskCommentModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.TASK)
class GetTaskCommentsCase(
    private val taskCommentService: ITaskCommentService,
) : TypedUseCase<GetTaskCommentsCase.Input, Flux<TaskCommentModel>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Flux<TaskCommentModel> {
        val taskComments = taskCommentService.getTaskComments(
            GetTaskCommentsIn(
                taskId = input.taskId
            )
        )
        return taskComments
    }

    data class Input(
        val taskId: String
    )
}