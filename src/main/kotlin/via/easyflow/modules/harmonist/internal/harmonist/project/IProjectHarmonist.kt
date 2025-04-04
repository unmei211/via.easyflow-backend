package via.easyflow.modules.harmonist.internal.harmonist.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.model.ProjectDetailsModel
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel

interface IProjectHarmonist {
    fun processCreateProject(createProjectIn: UpsertProjectIn): Mono<ProjectDetailsModel>
    fun processConnectMembers(connectMembersIn: ConnectMembersViaRolesIn): Flux<ProjectMemberViaRolesModel>
}