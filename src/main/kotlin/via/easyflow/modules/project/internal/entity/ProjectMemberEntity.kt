package via.easyflow.modules.project.internal.entity

import java.time.LocalDateTime

data class ProjectMemberEntity(
    val projectMemberId: String,
    val projectId: String,
    val userId: String,
    val joinedAt: LocalDateTime
)