package via.easyflow.modules.task.internal.repository.model

import java.time.Instant

data class TaskHistoryEntity(
    val taskHistoryId: String? = null,
    val taskId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
}