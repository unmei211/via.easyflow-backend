package via.easyflow.shared.modules.task.model

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