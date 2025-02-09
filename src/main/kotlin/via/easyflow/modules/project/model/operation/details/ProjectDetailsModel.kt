package via.easyflow.modules.project.model.operation.details

import via.easyflow.modules.project.model.operation.relation.ProjectOwnerModel
import java.time.Instant

data class ProjectDetailsModel(
    var projectId: String,
    var name: String,
    var description: String?,
    var createdAt: Instant,

    var members: List<ProjectMemberViaRoles>,
    var owner: ProjectOwnerModel,
)