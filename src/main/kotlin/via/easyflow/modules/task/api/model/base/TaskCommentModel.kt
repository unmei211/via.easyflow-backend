package via.easyflow.modules.task.api.model.base

import java.time.Instant

data class TaskCommentModel(
    val taskCommentId: String? = null,
    val taskId: String,
    val userId: String,
    val comment: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

}