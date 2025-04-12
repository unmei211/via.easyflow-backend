package via.easyflow.modules.project.api.interaction.service.member

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.database.query.resolver.IQueryFiltersResolver
import via.easyflow.core.tools.logger.logger
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.interaction.service.filter.model.ProjectMemberFilterModel
import via.easyflow.modules.project.api.model.ProjectMemberModel
import via.easyflow.modules.project.api.model.ProjectMemberRoleModel
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.internal.entity.ProjectMemberEntity
import via.easyflow.modules.project.internal.entity.ProjectMemberRoleEntity
import via.easyflow.modules.project.internal.repository.member.IMemberRepository
import via.easyflow.modules.project.internal.repository.member.contract.enquiry.ConnectMembersEnquiry
import via.easyflow.modules.project.internal.repository.member.contract.enquiry.GrantRolesToMemberEnquiry
import java.time.LocalDateTime
import kotlin.reflect.KClass

fun <T : Any, U : Any> IEnumerableLayerConverterManager<LayerType>.modelToEntity(fromTo: Pair<T, KClass<U>>) =
    getLayerConverter(LayerType.MODEL to LayerType.ENTITY).convert(fromTo)

@Service
class ProjectMemberService(
    private val memberRepository: IMemberRepository,
    @Qualifier("projectLayerConverter") private val cv: IEnumerableLayerConverterManager<LayerType>,
    private val projectMemberFilterResolver: IQueryFiltersResolver<ProjectMemberFilterModel>
) : IProjectMemberService {
    private val log = logger()
    override fun userExistsInProject(userExistsInProjectModuleInput: UserExistsInProjectModuleInput): Mono<Boolean> {
        val filters = projectMemberFilterResolver.resolve(
            model = ProjectMemberFilterModel(
                userId = userExistsInProjectModuleInput.userId,
                projectId = userExistsInProjectModuleInput.projectId,
            )
        )

        return memberRepository.existsMemberByFilters(filters)
    }


    override fun connectMembers(connectMembers: ConnectMembersIn): Flux<ProjectMemberModel> {
        val projectId = connectMembers.projectId
        val userIdsFlux = Flux.fromIterable(connectMembers.userIds)

        val connectedMemberFlux: Flux<ProjectMemberEntity> = userIdsFlux.flatMap { id ->
            memberRepository
                .connectMembersToProject(
                    ConnectMembersEnquiry(
                        Mono.just(
                            ProjectMemberEntity(
                                projectMemberId = uuid(),
                                projectId = projectId,
                                userId = id,
                                joinedAt = LocalDateTime.now(),
                            )
                        )
                    )
                )
        }
        return connectedMemberFlux
            .map { connectedMember ->
                cv.modelToEntity(connectedMember to ProjectMemberModel::class)
            }
    }

    override fun connectMembersViaRoles(connectMembersViaRolesIn: ConnectMembersViaRolesIn): Flux<ProjectMemberViaRolesModel> {
        log.info("Starting connectMembers for projectId: ${connectMembersViaRolesIn.projectId}")

        val projectId: String = connectMembersViaRolesIn.projectId
        val userRolesMap: Map<String, List<String>> = connectMembersViaRolesIn.userToRoles

        log.debug("Processing ${userRolesMap.size} user-role mappings")
        log.info("Entries: {}", userRolesMap.entries)
        val userEntryFlux: Flux<Map.Entry<String, List<String>>> =
            Flux.fromIterable(userRolesMap.entries)

        val projectMemberEntityFlux = userEntryFlux
            .doOnNext { log.info("Current entry $it") }
            .map { entry ->
                log.debug("Creating ProjectMemberModel for userId: ${entry.key}")
                ProjectMemberModel(
                    memberId = uuid(),
                    projectId = projectId,
                    userId = entry.key,
                    joinedAt = LocalDateTime.now(),
                )
            }
            .map { cv.modelToEntity(it to ProjectMemberEntity::class) }


        val connectedMemberFlux = projectMemberEntityFlux
            .flatMap { entity ->
                memberRepository.connectMembersToProject(
                    ConnectMembersEnquiry(Mono.just(entity))
                )
            }.doOnError { error ->
                log.error("Error connecting members to project: ${error.message}", error)
            }.doOnNext {
                log.info("Connected member with memberId: ${it.projectMemberId} to project")
            }


        val memberRoles: Flux<ProjectMemberRoleEntity> = memberRepository.grantRolesToMember(
            GrantRolesToMemberEnquiry(connectedMemberFlux.concatMap { connectedMember ->
                Flux
                    .fromIterable(userRolesMap[connectedMember.userId]!!)
                    .map { roleId ->
                        log.debug("Granting roleId: $roleId to memberId: ${connectedMember.projectMemberId}")
                        ProjectMemberRoleEntity(
                            projectMemberId = connectedMember.projectMemberId,
                            projectMemberRoleId = uuid(),
                            projectRoleId = roleId,
                            grantedAt = LocalDateTime.now(),
                        )
                    }
            })
        )
            .doOnError { error ->
                log.error("Error granting roles to member: ${error.message}", error)
            }

        val memberRolesByMemberId = memberRoles
            .map { role -> cv.modelToEntity(role to ProjectMemberRoleEntity::class) }
            .collectMultimap(
                { it.projectMemberId },
                { cv.modelToEntity(it to ProjectMemberRoleModel::class) }
            )
            .doOnSuccess {
                log.debug("Collected roles for ${it.size} members")
            }

        return connectedMemberFlux.flatMap { member ->
            memberRolesByMemberId.map { rolesMap ->
                val roles = rolesMap[member.userId].orEmpty()
                log.info("Returning ProjectMemberViaRolesModel for memberId: ${member.projectMemberId} with ${roles.size} roles")
                ProjectMemberViaRolesModel(
                    memberId = member.projectMemberId,
                    projectId = member.projectId,
                    joinedAt = member.joinedAt,
                    roles = roles.toList()
                )
            }
        }.doOnComplete {
            log.info("Completed connectMembers for projectId: $projectId")
        }.doOnError { error ->
            log.error("Error in final mapping for projectId: $projectId: ${error.message}", error)
        }
    }
}