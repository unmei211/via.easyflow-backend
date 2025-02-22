package via.easyflow.modules.project.api.service.role

import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.`in`.project.AddRoleIn
import via.easyflow.modules.project.api.models.`in`.role.ChangeDefaultRoleIn
import via.easyflow.modules.project.api.models.model.ProjectDefaultRoleModel
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel

interface IProjectRoleService {
    fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRolesModel>
    fun changeDefaultRole(defaultRoleIn: ChangeDefaultRoleIn):
            Mono<ProjectDefaultRoleModel>
}