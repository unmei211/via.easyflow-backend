package via.easyflow.modules.project.internal.repository.member

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles

class MemberRepository : IMemberRepository {
    override fun connectMembers(members: List<ProjectMemberEntity>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun grantRoles(membersRoles: List<ProjectMemberRoleEntity>): Flux<ProjectMemberViaRoles> {
        TODO("Not yet implemented")
    }
}