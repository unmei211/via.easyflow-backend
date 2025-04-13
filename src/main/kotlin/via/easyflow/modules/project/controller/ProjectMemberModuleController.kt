package via.easyflow.modules.project.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersViaRolesIn
import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel
import via.easyflow.shared.modules.project.api.controller.IProjectMemberModuleController
import via.easyflow.shared.modules.project.api.service.IProjectMemberService

@Controller
class ProjectMemberModuleController(
    private val memberService: IProjectMemberService
) : IProjectMemberModuleController {
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