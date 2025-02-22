package via.easyflow.modules.project.internal.entity

import java.time.LocalDateTime

data class ProjectMemberRoleEntity(
    var projectMemberRoleId: String,
    var projectMemberId: String,
    var projectRoleId: String,
    var grantedAt: LocalDateTime
)