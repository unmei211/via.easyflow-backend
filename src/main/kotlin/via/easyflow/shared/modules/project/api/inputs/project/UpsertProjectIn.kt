package via.easyflow.shared.modules.project.api.inputs.project

import via.easyflow.shared.modules.project.model.ProjectModel

data class UpsertProjectIn(
    var project: ProjectModel,
    var ownerId: String
)