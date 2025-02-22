package via.easyflow.modules.project.api.models.model

import java.time.Instant

data class ProjectMemberRoleModel(
    var memberRoleId: String,
    var memberId: String,
    var roleId: String,
    var assignedAt: Instant,
)