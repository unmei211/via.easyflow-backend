package via.easyflow.modules.task.repository.model

import java.time.Instant
import via.easyflow.shared.modules.task.model.SubtaskModel

data class SubtaskEntity(
    val subtaskId: String,
    val taskId: String,
    val title: String,
    val ownerId: String,
    val description: String?,
    val assigneeId: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun toModel(): SubtaskModel {
        return SubtaskModel(
            subtaskId = subtaskId,
            taskId = taskId,
            title = title,
            ownerId = ownerId,
            description = description,
            assigneeId = assigneeId,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}