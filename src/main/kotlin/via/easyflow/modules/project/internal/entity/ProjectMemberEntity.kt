package via.easyflow.modules.project.internal.entity

import java.time.Instant

data class ProjectMemberEntity(
    val projectMemberId: String,
    val projectId: String,
    val userId: String,
    val joinedAt: Instant
)