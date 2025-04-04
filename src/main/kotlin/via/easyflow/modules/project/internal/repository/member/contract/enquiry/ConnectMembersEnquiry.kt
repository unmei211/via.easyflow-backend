package via.easyflow.modules.project.internal.repository.member.contract.enquiry

import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity

data class ConnectMembersEnquiry(
    val projectMemberEntity: Mono<ProjectMemberEntity>
)