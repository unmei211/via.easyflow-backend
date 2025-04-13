package via.easyflow.modules.project.repository.member.model

import java.time.LocalDateTime

data class ProjectMemberRoleEntity(
    var projectMemberRoleId: String,
    var projectMemberId: String,
    var projectRoleId: String,
    var grantedAt: LocalDateTime
)