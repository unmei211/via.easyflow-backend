package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.GetTaskCommentsIn
import via.easyflow.shared.modules.task.api.service.ITaskCommentService
import via.easyflow.shared.modules.task.model.TaskCommentModel

@Component
class GetTaskCommentsCase(
    private val taskCommentService: ITaskCommentService,
) : UseCase<GetTaskCommentsCase.Input, Flux<TaskCommentModel>> {
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