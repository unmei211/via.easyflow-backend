package via.easyflow.modules.project.model.operation.relation

import java.time.Instant

data class ProjectMemberModel(
    val memberId: String,
    val projectId: String,
    val userId: String,
    val joinedAt: Instant
)