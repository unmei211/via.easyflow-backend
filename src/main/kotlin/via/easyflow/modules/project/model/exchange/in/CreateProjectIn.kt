package via.easyflow.modules.project.model.exchange.`in`

import via.easyflow.modules.project.model.operation.relation.ProjectModel

data class CreateProjectIn(
    var project: ProjectModel,
    var ownerId: String
)