package via.easyflow.modules.project.api.interaction.controller.member

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService

@Controller
class ProjectMemberController(
    private val memberService: IProjectMemberService
) : IProjectMemberController {
    private val log = logger()
    private fun validateConnectMembersToProject(connectMembersRequest: ConnectMembersViaRolesIn, projectId: String) {
        if (connectMembersRequest.projectId != projectId) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    override fun connectMembersToProject(
        connectMembersRequest: ConnectMembersViaRolesIn,
        projectId: String
    ): ResponseEntity<Flux<ProjectMemberViaRolesModel>> {
        validateConnectMembersToProject(connectMembersRequest, projectId)

        return ResponseEntity.ok(
            memberService.connectMembersViaRoles(
                ConnectMembersViaRolesIn(
                    projectId = projectId,
                    userToRoles = connectMembersRequest.userToRoles
                )
            )
        )
    }

    override fun getMembers(): Mono<ResponseEntity<Flux<Void>>> {
        TODO("Not yet implemented")
    }
}