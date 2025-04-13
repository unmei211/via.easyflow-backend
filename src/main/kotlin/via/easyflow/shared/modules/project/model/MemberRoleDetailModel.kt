package via.easyflow.shared.modules.project.model

import java.time.LocalDateTime

data class MemberRoleDetailModel(
    var memberRoleId: String,
    var memberId: String,
    var projectRoleId: String,
    var roleId: String,
    var name: String,
    var description: String?,
    var color: String,
    var roleGrantedAt: LocalDateTime,
)