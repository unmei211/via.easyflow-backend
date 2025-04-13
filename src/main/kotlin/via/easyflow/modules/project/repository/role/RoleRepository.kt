package via.easyflow.modules.project.repository.role

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import via.easyflow.modules.project.repository.role.model.ProjectDefaultRoleEntity

@Repository
class RoleRepository : via.easyflow.modules.project.repository.role.IRoleRepository {
    override fun changeDefaultRole(defaultRoleEntity: ProjectDefaultRoleEntity): Mono<ProjectDefaultRoleEntity> {
        TODO("Not yet implemented")
    }
}