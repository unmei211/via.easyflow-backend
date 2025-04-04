package via.easyflow.modules.project.api.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ProjectMemberViaRolesModel(
    var memberId: String,
    var projectId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    var joinedAt: LocalDateTime,
    var roles: List<ProjectMemberRoleModel>
)