package via.easyflow.modules.project.api.contract.`in`.project

import via.easyflow.modules.project.api.model.ProjectModel

data class UpsertProjectIn(
    var project: ProjectModel,
    var ownerId: String
)