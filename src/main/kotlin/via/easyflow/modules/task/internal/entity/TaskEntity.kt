package via.easyflow.modules.task.internal.entity

import java.time.Instant

data class TaskEntity(
    val taskId: String? = null,
    val description: String? = null,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val ownerUserId: String,
    val projectId: String
) {

}