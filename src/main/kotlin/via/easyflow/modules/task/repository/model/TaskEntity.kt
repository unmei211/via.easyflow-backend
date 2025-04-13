package via.easyflow.modules.task.repository.model

import java.time.Instant

data class TaskEntity(
    val name: String,
    val taskId: String,
    val description: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val ownerUserId: String,
    val projectId: String,
    val version: String
) {

}