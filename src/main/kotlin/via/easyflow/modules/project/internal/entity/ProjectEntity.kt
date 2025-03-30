package via.easyflow.modules.project.internal.entity

import java.time.LocalDateTime

data class ProjectEntity(
    var projectId: String,
    var name: String,
    var description: String?,
    var createdAt: LocalDateTime,
)