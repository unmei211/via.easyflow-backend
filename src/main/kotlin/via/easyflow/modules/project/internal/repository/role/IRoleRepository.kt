package via.easyflow.modules.project.internal.repository.role

import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectDefaultRoleEntity

interface IRoleRepository {
    fun changeDefaultRole(defaultRoleEntity: ProjectDefaultRoleEntity): Mono<ProjectDefaultRoleEntity>
}