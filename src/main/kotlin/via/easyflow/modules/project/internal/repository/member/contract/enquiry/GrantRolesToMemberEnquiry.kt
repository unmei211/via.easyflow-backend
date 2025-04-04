package via.easyflow.modules.project.internal.repository.member.contract.enquiry

import reactor.core.publisher.Flux
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity

data class GrantRolesToMemberEnquiry(
    val memberRoleEntities: Flux<ProjectMemberRoleEntity>,
)