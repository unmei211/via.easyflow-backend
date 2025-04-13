package via.easyflow.shared.modules.project.model

import java.time.LocalDateTime

data class ProjectModel(
    var projectId: String? = null,
    var name: String,
    var description: String? = null,
    var createdAt: LocalDateTime? = null,
)