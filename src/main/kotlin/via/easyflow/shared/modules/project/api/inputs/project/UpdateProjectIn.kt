package via.easyflow.shared.modules.project.api.inputs.project

import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.shared.modules.project.model.ProjectOwnerModel

data class UpdateProjectIn(
    var project: ProjectModel,

    var members: List<ProjectMemberViaRolesModel>,
    var owner: ProjectOwnerModel,
)