package via.easyflow.modules.project.internal.repository.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.entity.ProjectOwnerEntity
import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles

interface IProjectRepository {
    fun getProjects(): Flux<ProjectEntity>
    fun getProjectById(projectId: String): Mono<ProjectEntity>
    fun upsertProject(project: ProjectEntity): Mono<Void>
    fun changeProjectOwner(owner: ProjectOwnerEntity): Mono<Void>
    fun deleteProjectById(id: String): Mono<Void>
}