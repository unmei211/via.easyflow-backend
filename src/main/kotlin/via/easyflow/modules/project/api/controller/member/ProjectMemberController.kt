package via.easyflow.modules.project.api.controller.member

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.models.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.models.model.ProjectMemberModel
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.api.service.member.IProjectMemberService
import java.time.LocalDateTime

@Controller
class ProjectMemberController(
    private val memberService: IProjectMemberService
) : IProjectMemberController {
    private val log = logger()
    private fun validateConnectMembersToProject(connectMembersRequest: ConnectMembersIn, projectId: String) {
        if (connectMembersRequest.projectId != projectId) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    override fun connectMembersToProject(
        connectMembersRequest: ConnectMembersIn,
        projectId: String
    ): ResponseEntity<Flux<ProjectMemberViaRolesModel>> {
        validateConnectMembersToProject(connectMembersRequest, projectId)

        return ResponseEntity.ok(
            memberService.connectMembers(
                ConnectMembersIn(
                    projectId = projectId,
                    userToRoles = connectMembersRequest.userToRoles
                )
            )
        )
    }

    override fun getMembers(): Mono<ResponseEntity<Flux<Void>>> {
        TODO("Not yet implemented")
    }

    override fun example(connectMembersRequest: ConnectMembersIn, projectId: String): ResponseEntity<Flux<ProjectMemberModel>> {
        log.info("Starting connectMembers for projectId: ${connectMembersRequest.projectId}")

        val projectId: String = connectMembersRequest.projectId
        val userRolesMap: Map<String, List<String>> = connectMembersRequest.userToRoles

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
        return ResponseEntity.ok(projectMemberEntityFlux)
    }
}