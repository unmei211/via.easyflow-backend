package via.easyflow.modules.project.api.interaction.service.role

import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.contract.`in`.project.AddRoleIn
import via.easyflow.modules.project.api.contract.`in`.role.ChangeDefaultRoleIn
import via.easyflow.modules.project.api.model.ProjectDefaultRoleModel
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel

interface IProjectRoleService {
    fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRolesModel>
    fun changeDefaultRole(defaultRoleIn: ChangeDefaultRoleIn):
            Mono<ProjectDefaultRoleModel>
}