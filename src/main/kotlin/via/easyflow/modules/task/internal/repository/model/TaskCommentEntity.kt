package via.easyflow.modules.task.internal.repository.model

import java.time.Instant

data class TaskCommentEntity(
    val taskCommentId: String? = null,
    val taskId: String,
    val userId: String,
    val comment: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

}