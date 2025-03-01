package via.easyflow.modules.harmonist.internal.harmonist.project

import reactor.core.publisher.Flux
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel

interface IProjectHarmonist {
    fun processConnectMembers(connectMembersIn: ConnectMembersIn): Flux<ProjectMemberViaRolesModel>
}