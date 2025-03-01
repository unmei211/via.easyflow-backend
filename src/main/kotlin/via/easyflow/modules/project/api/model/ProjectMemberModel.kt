package via.easyflow.modules.project.api.model

import java.time.Instant
import java.time.LocalDateTime

data class ProjectMemberModel(
    val memberId: String,
    val projectId: String,
    val userId: String,
    val joinedAt: LocalDateTime,
)