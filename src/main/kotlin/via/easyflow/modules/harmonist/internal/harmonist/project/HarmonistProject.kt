package via.easyflow.modules.harmonist.internal.harmonist.project

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.auth.api.contract.`in`.user.ExistsUserIn
import via.easyflow.modules.auth.api.interaction.service.user.IUserService
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.interaction.service.project.IProjectService
import via.easyflow.modules.project.api.model.*

@Service
class HarmonistProject(
    private val projectService: IProjectService,
    private val memberService: IProjectMemberService,
    private val userService: IUserService,
) : IProjectHarmonist {
    private val log = logger()

    override fun processCreateProject(createProjectIn: UpsertProjectIn): Mono<ProjectDetailsModel> {
        val nextFlagMono = userService
            .existsUser(ExistsUserIn(createProjectIn.ownerId))
            .doOnSubscribe { log.info("🟡 Starting user existence check for ownerId: ${createProjectIn.ownerId}") }
            .doOnNext { log.debug("🟢 User existence check result: $it") }
            .doOnError { log.error("🔴 Error checking user existence", it) }
            .flatMap { isExists ->
                if (isExists) {
                    log.info("🟢 User ${createProjectIn.ownerId} exists")
                    Mono.just(isExists)
                } else {
                    log.warn("🟠 User ${createProjectIn.ownerId} not found")
                    Mono.error { NotFoundException("Not found user") }
                }
            }

        val upsertProjectMono: Mono<Pair<ProjectModel, ProjectOwnerModel>> = nextFlagMono
            .doOnSubscribe { log.info("🟡 Starting project upsert for: ${createProjectIn}") }
            .flatMap { projectService.upsertProject(createProjectIn) }
            .doOnNext { (project, owner) ->
                log.info("🟢 Project upserted successfully. ID: ${project.projectId}, Owner: ${owner.userId}")
            }
            .doOnError { log.error("🔴 Error during project upsert", it) }

        val connectMembersMono =
            upsertProjectMono
                .doOnSubscribe { log.info("🟡 Starting member connection process") }
                .flatMap { (project, projectOwner) ->
                    log.info("🔗 Connecting owner to project ${project.projectId}")

                    val connectOwnerMono = memberService.connectMembers(
                        ConnectMembersIn(
                            projectId = project.projectId!!,
                            userIds = listOf(projectOwner.userId),
                        )
                    )
                        .next()
                        .doOnNext { log.info("🤝 Owner connected successfully. Member ID: ${it.memberId}") }
                        .doOnError { log.error("🔗 Error connecting owner", it) }

                    val projectBundle = Mono.just(project to projectOwner)
                        .doOnNext { log.debug("📦 Project bundle prepared") }

                    Mono.zip(connectOwnerMono, projectBundle)
                }

        return connectMembersMono
            .doOnSubscribe { log.info("🟡 Assembling final project details") }
            .map { bundle ->
                log.debug("🧩 Processing result bundle")
                val project: ProjectModel = bundle.t2.first
                val owner: ProjectOwnerModel = bundle.t2.second
                val member: ProjectMemberModel = bundle.t1

                log.info("🎉 Successfully created project ${project.projectId}")
                ProjectDetailsModel(
                    projectId = project.projectId!!,
                    name = project.name,
                    description = project.description,
                    createdAt = project.createdAt!!,
                    members = listOf(member),
                    owner = owner,
                )
            }
            .doOnError { log.error("💥 Critical error in project creation flow", it) }
    }

    override fun processConnectMembers(connectMembersIn: ConnectMembersViaRolesIn): Flux<ProjectMemberViaRolesModel> {
        val existsMono = projectService.projectIsExists(connectMembersIn.projectId).flatMap {
            if (!it) {
                Mono.error {
                    NotFoundException("Not found project")
                }
            } else {
                Mono.just(it)
            }
        }

        val connectMemberFlux = existsMono.flatMapMany {
            memberService.connectMembersViaRoles(connectMembersViaRolesIn = connectMembersIn)
        }

        return connectMemberFlux
    }
}