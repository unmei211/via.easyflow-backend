package via.easyflow.modules.task.internal.repository.model

import java.time.Instant

data class TaskHistoryEntity(
    val taskHistoryId: String? = null,
    val taskId: String,
    val name: String,
    val description: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val ownerUserId: String,
    val changerId: String,
    val projectId: String,
    val version: String,
    val moveAt: Instant
) {
}