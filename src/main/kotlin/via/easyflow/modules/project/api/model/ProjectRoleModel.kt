package via.easyflow.modules.project.api.model

import java.time.Instant

data class ProjectRoleModel(
    var roleId: String?,
    var projectId: String,
    var name: String,
    var description: String?,
    var createdAt: Instant?,
    var color: String,
)