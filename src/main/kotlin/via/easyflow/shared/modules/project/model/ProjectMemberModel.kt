package via.easyflow.shared.modules.project.model

import java.time.LocalDateTime

data class ProjectMemberModel(
    val memberId: String,
    val projectId: String,
    val userId: String,
    val joinedAt: LocalDateTime,
)