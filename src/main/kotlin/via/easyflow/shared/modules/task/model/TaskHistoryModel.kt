package via.easyflow.shared.modules.task.model

import java.time.Instant

data class TaskHistoryModel(
    val taskHistoryId: String? = null,
    val taskId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
}