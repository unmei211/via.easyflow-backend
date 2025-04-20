package via.easyflow.modules.task.repository.model

import java.time.Instant
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.shared.modules.task.model.TaskCommentModel

data class TaskCommentEntity(
    val taskCommentId: String,
    val taskId: String,
    val userId: String,
    val comment: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun new(
            userId: String,
            comment: String,
            taskId: String,
        ) = TaskCommentEntity(
            taskCommentId = uuid(),
            taskId = taskId,
            userId = userId,
            comment = comment,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

    fun toModel(): TaskCommentModel {
        return TaskCommentModel(
            taskCommentId = taskCommentId,
            taskId = taskId,
            userId = userId,
            comment = comment,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}