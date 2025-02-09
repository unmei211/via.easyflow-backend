package via.easyflow.modules.project.internal.entity

import java.time.Instant

data class ProjectEntity(
    var projectId: String,
    var name: String,
    var description: String?,
    var createdAt: Instant,
)