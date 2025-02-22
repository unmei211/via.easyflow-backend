package via.easyflow.modules.project.api.service.member

import org.springframework.beans.factory.annotation.Qualifier
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.models.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.models.`in`.project.AddRoleIn
import via.easyflow.modules.project.api.models.model.ProjectMemberModel
import via.easyflow.modules.project.api.models.model.ProjectMemberRoleModel
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.repository.member.MemberRepository
import via.easyflow.modules.project.internal.repository.member.model.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.internal.repository.member.model.enquiry.GrantRolesToMemberEnquiry
import java.time.LocalDateTime
import kotlin.reflect.KClass

fun <T : Any, U : Any> IEnumerableLayerConverterManager<LayerType>.modelToEntity(fromTo: Pair<T, KClass<U>>) =
    getLayerConverter(LayerType.MODEL to LayerType.ENTITY).convert(fromTo)

class ProjectMemberService(
    private val memberRepository: MemberRepository,
    @Qualifier("projectLayerConverter") private val cv: IEnumerableLayerConverterManager<LayerType>
) : IProjectMemberService {

    override fun connectMembers(connectMembersIn: ConnectMembersIn): Flux<ProjectMemberViaRolesModel> {
        val projectId: String = connectMembersIn.projectId
        val userRolesMap: Map<String, List<String>> = connectMembersIn.userToRoles

        val userEntryFlux: Flux<Map.Entry<String, List<String>>> =
            Flux.fromIterable(userRolesMap.entries)

        val projectMemberEntityFlux = userEntryFlux
            .map { entry ->
                ProjectMemberModel(
                    memberId = uuid(),
                    projectId = projectId,
                    userId = entry.key,
                    joinedAt = LocalDateTime.now(),
                )
            }
            .map { cv.modelToEntity(it to ProjectMemberEntity::class) }

        val connectedMemberFlux: Flux<ProjectMemberEntity> =
            memberRepository.connectMembersToProject(ConnectMembersEnquiry(projectMemberEntityFlux))

        val memberRoles: Flux<ProjectMemberRoleEntity> = memberRepository.grantRolesToMember(
            GrantRolesToMemberEnquiry(connectedMemberFlux.concatMap { connectedMember ->
                Flux
                    .fromIterable(userRolesMap[connectedMember.userId]!!)
                    .map { roleId ->
                        ProjectMemberRoleEntity(
                            projectMemberId = connectedMember.projectMemberId,
                            projectMemberRoleId = uuid(),
                            projectRoleId = roleId,
                            grantedAt = LocalDateTime.now(),
                        )
                    }
            })
        )

        val memberRolesByMemberId = memberRoles
            .map { role -> cv.modelToEntity(role to ProjectMemberRoleEntity::class) }
            .collectMultimap(
                {
                    it.projectMemberId
                },
                {
                    cv.modelToEntity(it to ProjectMemberRoleModel::class)
                }
            )

        return connectedMemberFlux.flatMap { member ->
            memberRolesByMemberId.map { rolesMap ->
                val roles = rolesMap[member.userId].orEmpty()
                ProjectMemberViaRolesModel(
                    memberId = member.projectMemberId,
                    projectId = member.projectId,
                    joinedAt = member.joinedAt,
                    roles = roles.toList()
                )
            }
        }
    }
}