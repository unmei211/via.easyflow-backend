package via.easyflow.shared.modules.task.model.flow

import reactor.core.publisher.Flux
import via.easyflow.shared.modules.task.model.SubtaskModel
import via.easyflow.shared.modules.task.model.TaskCommentModel

class TaskInfoFlow(
    val taskId: String,
    val taskName: String,
    val ownerId: String,
    val status: String,
    val comments: Flux<TaskCommentModel>,
    val subtasks: Flux<SubtaskModel>,
) {
}