package via.easyflow.modules.project.repository.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.repository.project.model.ProjectEntity
import via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity

interface IProjectRepository {
    fun getProjects(): Flux<via.easyflow.modules.project.repository.project.model.ProjectEntity>
    fun getProjectById(projectId: String): Mono<via.easyflow.modules.project.repository.project.model.ProjectEntity>
    fun upsertProject(project: via.easyflow.modules.project.repository.project.model.ProjectEntity): Mono<Void>
    fun changeProjectOwner(owner: via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity): Mono<Void>
    fun deleteProjectById(id: String): Mono<Void>
    fun existsProjectById(projectId: String): Mono<Boolean>
}