package via.easyflow.modules.project.api.models.`in`.project

import via.easyflow.modules.project.api.models.model.ProjectModel

data class UpsertProjectIn(
    var project: ProjectModel,
    var ownerId: String
)