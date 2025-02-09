package via.easyflow.modules.project.model.operation.details

import via.easyflow.modules.project.model.operation.relation.ProjectRoleModel
import java.time.Instant

data class ProjectMemberViaRoles(
    var memberId: String,
    var projectId: String,
    var joinedAt: Instant,
    var roles: List<ProjectRoleModel>
)