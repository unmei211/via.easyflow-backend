package via.easyflow.modules.project.repository.member

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.modules.project.repository.member.contract.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.repository.member.contract.enquiry.GrantRolesToMemberEnquiry
import via.easyflow.modules.project.repository.member.model.ProjectMemberEntity
import via.easyflow.modules.project.repository.member.model.ProjectMemberRoleEntity

interface IMemberRepository {
    fun connectMembersToProject(connectMembersEnquiry: ConnectMembersEnquiry): Mono<ProjectMemberEntity>
    fun grantRolesToMember(grantEnquiry: GrantRolesToMemberEnquiry): Flux<ProjectMemberRoleEntity>;

    fun existsMemberByFilters(filters: List<IQueryFilter>): Mono<Boolean>;

    fun searchByFilter(filters: List<IQueryFilter>): Flux<ProjectMemberEntity>;
}