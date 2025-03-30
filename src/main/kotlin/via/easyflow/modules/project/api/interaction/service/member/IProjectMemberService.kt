package via.easyflow.modules.project.api.interaction.service.member

import reactor.core.publisher.Flux
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.model.ProjectMemberModel
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel

interface IProjectMemberService {
    fun connectMembersViaRoles(connectMembersViaRolesIn: ConnectMembersViaRolesIn): Flux<ProjectMemberViaRolesModel>
    fun connectMembers(connectMembers: ConnectMembersIn): Flux<ProjectMemberModel>
}