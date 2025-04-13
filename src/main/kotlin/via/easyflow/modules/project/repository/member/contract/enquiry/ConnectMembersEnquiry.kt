package via.easyflow.modules.project.repository.member.contract.enquiry

import reactor.core.publisher.Mono
import via.easyflow.modules.project.repository.member.model.ProjectMemberEntity

data class ConnectMembersEnquiry(
    val projectMemberEntity: Mono<ProjectMemberEntity>
)