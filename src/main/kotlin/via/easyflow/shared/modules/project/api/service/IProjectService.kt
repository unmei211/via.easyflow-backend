package via.easyflow.shared.modules.project.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.api.inputs.project.UpsertProjectIn
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.shared.modules.project.model.ProjectOwnerModel

interface IProjectService {
    fun upsertProject(upsertRequest: UpsertProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>>
    fun getProjects(): Flux<ProjectModel>
    fun projectIsExists(projectId: String): Mono<Boolean>
}