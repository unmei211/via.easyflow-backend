package via.easyflow.modules.project.api.models.`in`.project

import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.api.models.model.ProjectModel
import via.easyflow.modules.project.api.models.model.ProjectOwnerModel

data class UpdateProjectIn(
    var project: ProjectModel,

    var members: List<ProjectMemberViaRolesModel>,
    var owner: ProjectOwnerModel,
)