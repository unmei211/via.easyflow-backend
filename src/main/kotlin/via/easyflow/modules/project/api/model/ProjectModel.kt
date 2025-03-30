package via.easyflow.modules.project.api.model

import java.time.LocalDateTime

data class ProjectModel(
    var projectId: String?,
    var name: String,
    var description: String?,
    var createdAt: LocalDateTime?,
)