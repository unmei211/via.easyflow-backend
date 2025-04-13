package via.easyflow.shared.modules.project.api.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersViaRolesIn
import via.easyflow.shared.modules.project.api.inputs.member.GetProjectMembersModuleIn
import via.easyflow.shared.modules.project.model.ProjectMemberModel
import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel

interface IProjectMemberService {
    fun connectMembersViaRoles(connectMembersViaRolesIn: ConnectMembersViaRolesIn): Flux<ProjectMemberViaRolesModel>
    fun connectMembers(connectMembers: ConnectMembersIn): Flux<ProjectMemberModel>
    fun userExistsInProject(userExistsInProjectModuleInput: UserExistsInProjectModuleInput): Mono<Boolean>
    fun getProjectMembers(input: GetProjectMembersModuleIn): Flux<ProjectMemberModel>
}

data class UserExistsInProjectModuleInput(
    val userId: String,
    val projectId: String,
)