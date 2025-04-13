package via.easyflow.modules.project.repository.member.contract.enquiry

import reactor.core.publisher.Flux
import via.easyflow.modules.project.repository.member.model.ProjectMemberRoleEntity

data class GrantRolesToMemberEnquiry(
    val memberRoleEntities: Flux<ProjectMemberRoleEntity>,
)