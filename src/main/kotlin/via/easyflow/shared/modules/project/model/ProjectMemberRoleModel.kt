package via.easyflow.shared.modules.project.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class ProjectMemberRoleModel(
    var memberRoleId: String,
    var memberId: String,
    var roleId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    var assignedAt: Instant,
)