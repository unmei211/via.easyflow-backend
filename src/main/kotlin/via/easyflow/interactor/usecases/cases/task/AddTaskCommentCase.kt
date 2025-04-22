package via.easyflow.interactor.usecases.cases.task

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.shared.modules.task.api.inputs.task_comment.PushTaskCommentIn
import via.easyflow.shared.modules.task.api.service.ITaskCommentService
import via.easyflow.shared.modules.task.model.TaskCommentModel

@Component
class AddTaskCommentCase(
    private val taskCommentService: ITaskCommentService
) : UseCase<AddTaskCommentCase.Input, Mono<TaskCommentModel>> {
    override fun invoke(input: Input): Mono<TaskCommentModel> {
        return taskCommentService.pushTaskComment(
            input = PushTaskCommentIn(
                taskId = input.taskId,
                userId = input.userId,
                comment = input.comment
            )
        )
    }

    data class Input(val taskId: String, val userId: String, val comment: String)
}