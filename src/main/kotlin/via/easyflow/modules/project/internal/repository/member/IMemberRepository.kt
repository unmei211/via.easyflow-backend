package via.easyflow.modules.project.internal.repository.member

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.repository.member.model.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.internal.repository.member.model.enquiry.GrantRolesToMemberEnquiry

interface IMemberRepository {
    fun connectMembersToProject(connectMembersEnquiry: ConnectMembersEnquiry): Flux<ProjectMemberEntity>
    fun grantRolesToMember(grantEnquiry: GrantRolesToMemberEnquiry): Flux<ProjectMemberRoleEntity>;
}