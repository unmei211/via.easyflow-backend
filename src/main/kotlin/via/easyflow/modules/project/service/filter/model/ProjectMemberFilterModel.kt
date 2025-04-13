package via.easyflow.modules.project.service.filter.model

data class ProjectMemberFilterModel(
    val userId: String? = null,
    val projectId: String? = null,
    val projectMemberId: String? = null
) {
}