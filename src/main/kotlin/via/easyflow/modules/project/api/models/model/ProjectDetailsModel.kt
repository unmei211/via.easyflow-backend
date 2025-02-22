package via.easyflow.modules.project.api.models.model

import java.time.Instant

data class ProjectDetailsModel(
    var projectId: String,
    var name: String,
    var description: String?,
    var createdAt: Instant,

    var members: List<ProjectMemberViaRolesModel>,
    var owner: ProjectOwnerModel,
)