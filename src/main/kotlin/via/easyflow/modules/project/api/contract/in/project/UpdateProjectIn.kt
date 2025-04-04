package via.easyflow.modules.project.api.contract.`in`.project

import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.api.model.ProjectModel
import via.easyflow.modules.project.api.model.ProjectOwnerModel

data class UpdateProjectIn(
    var project: ProjectModel,

    var members: List<ProjectMemberViaRolesModel>,
    var owner: ProjectOwnerModel,
)