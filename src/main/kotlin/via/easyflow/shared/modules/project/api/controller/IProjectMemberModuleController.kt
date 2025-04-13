package via.easyflow.shared.modules.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersViaRolesIn
import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel

@RestController
@RequestMapping("/module/projects/{projectId}/members")
interface IProjectMemberModuleController {
    @PostMapping("/connect")
    fun connectMembersToProject(
        @RequestBody connectMembersRequest: ConnectMembersViaRolesIn,
        @PathVariable projectId: String
    ): ResponseEntity<Flux<ProjectMemberViaRolesModel>>

    @GetMapping("/")
    fun getMembers(): Mono<ResponseEntity<Flux<Void>>>
}