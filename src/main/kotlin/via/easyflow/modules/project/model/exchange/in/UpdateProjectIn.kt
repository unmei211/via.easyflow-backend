package via.easyflow.modules.project.model.exchange.`in`

import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles
import via.easyflow.modules.project.model.operation.relation.ProjectModel
import via.easyflow.modules.project.model.operation.relation.ProjectOwnerModel
import java.time.Instant

data class UpdateProjectIn(
    var project: ProjectModel,

    var members: List<ProjectMemberViaRoles>,
    var owner: ProjectOwnerModel,
)