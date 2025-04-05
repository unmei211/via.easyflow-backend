package via.easyflow.modules.task.api.model.base

import java.time.Instant

data class TaskHistoryModel(
    val taskHistoryId: String? = null,
    val taskId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
}