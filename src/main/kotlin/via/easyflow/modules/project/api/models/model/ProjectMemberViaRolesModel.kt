package via.easyflow.modules.project.api.models.model

import java.time.LocalDateTime

data class ProjectMemberViaRolesModel(
    var memberId: String,
    var projectId: String,
    var joinedAt: LocalDateTime,
    var roles: List<ProjectMemberRoleModel>
)