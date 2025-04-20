package via.easyflow.shared.modules.task.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.task.api.inputs.GetTaskCommentsIn
import via.easyflow.shared.modules.task.api.inputs.task_comment.PushTaskCommentIn
import via.easyflow.shared.modules.task.model.TaskCommentModel

interface ITaskCommentService {
    fun pushTaskComment(input: PushTaskCommentIn): Mono<TaskCommentModel>
    fun getTaskComments(input: GetTaskCommentsIn): Flux<TaskCommentModel>
}