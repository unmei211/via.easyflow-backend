package via.easyflow.modules.project.api.service.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.models.model.ProjectModel
import via.easyflow.modules.project.api.models.model.ProjectOwnerModel

interface IProjectService {
    fun upsertProject(upsertRequest: UpsertProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>>
    fun getProjects(): Flux<ProjectModel>
}