package via.easyflow.modules.project.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.model.exchange.`in`.AddRoleIn
import via.easyflow.modules.project.model.exchange.`in`.ConnectMembersIn
import via.easyflow.modules.project.model.exchange.`in`.UpsertProjectIn
import via.easyflow.modules.project.model.exchange.`in`.UpdateProjectIn
import via.easyflow.modules.project.model.operation.relation.ProjectModel
import via.easyflow.modules.project.model.operation.relation.ProjectOwnerModel
import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles

interface IProjectService {
    fun upsertProject(upsertRequest: UpsertProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>>
    fun connectMembers(connectMembersRequest: ConnectMembersIn): Flux<ProjectMemberViaRoles>
    fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRoles>
    fun getProjects(): Flux<ProjectModel>
}