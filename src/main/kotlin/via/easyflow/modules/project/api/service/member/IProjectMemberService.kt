package via.easyflow.modules.project.api.service.member

import reactor.core.publisher.Flux
import via.easyflow.modules.project.api.models.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel

interface IProjectMemberService {
    fun connectMembers(connectMembersIn: ConnectMembersIn): Flux<ProjectMemberViaRolesModel>
}