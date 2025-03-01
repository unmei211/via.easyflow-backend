package via.easyflow.modules.project.api.interaction.service.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.model.ProjectModel
import via.easyflow.modules.project.api.model.ProjectOwnerModel

interface IProjectService {
    fun upsertProject(upsertRequest: UpsertProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>>
    fun getProjects(): Flux<ProjectModel>
    fun projectIsExists(projectId: String): Mono<Boolean>
}