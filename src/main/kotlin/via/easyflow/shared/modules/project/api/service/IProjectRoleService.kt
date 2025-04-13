package via.easyflow.shared.modules.project.api.service

import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.api.inputs.project.AddRoleIn
import via.easyflow.shared.modules.project.api.inputs.role.ChangeDefaultRoleIn
import via.easyflow.shared.modules.project.model.ProjectDefaultRoleModel
import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel

interface IProjectRoleService {
    fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRolesModel>
    fun changeDefaultRole(defaultRoleIn: ChangeDefaultRoleIn):
            Mono<ProjectDefaultRoleModel>
}