package via.easyflow.modules.project.api.interaction.controller.member

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.model.ProjectMemberModel
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel

@RestController
@RequestMapping("/projects/{projectId}/members")
interface IProjectMemberController {
    @PostMapping("/connect")
    fun connectMembersToProject(
        @RequestBody connectMembersRequest: ConnectMembersViaRolesIn,
        @PathVariable projectId: String
    ): ResponseEntity<Flux<ProjectMemberViaRolesModel>>

    @GetMapping("/")
    fun getMembers(): Mono<ResponseEntity<Flux<Void>>>
}