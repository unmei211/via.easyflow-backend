package via.easyflow.modules.project.internal.repository.member.model.enquiry

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity

data class ConnectMembersEnquiry(
    val projectMemberEntity: Mono<ProjectMemberEntity>
)