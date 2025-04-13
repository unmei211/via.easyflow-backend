package via.easyflow.modules.project.repository.role

import reactor.core.publisher.Mono
import via.easyflow.modules.project.repository.role.model.ProjectDefaultRoleEntity

interface IRoleRepository {
    fun changeDefaultRole(defaultRoleEntity: ProjectDefaultRoleEntity): Mono<ProjectDefaultRoleEntity>
}