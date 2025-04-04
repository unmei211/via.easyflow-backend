package via.easyflow.modules.project.internal.repository.role

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectDefaultRoleEntity

@Repository
class RoleRepository : IRoleRepository {
    override fun changeDefaultRole(defaultRoleEntity: ProjectDefaultRoleEntity): Mono<ProjectDefaultRoleEntity> {
        TODO("Not yet implemented")
    }
}