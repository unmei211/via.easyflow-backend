package via.easyflow.modules.project.api.controller.member

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.models.model.ProjectMemberModel
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel

@RestController
@RequestMapping("/projects/{projectId}/members")
interface IProjectMemberController {
    @PostMapping("/connect")
    fun connectMembersToProject(
        @RequestBody connectMembersRequest: ConnectMembersIn,
        @PathVariable projectId: String
    ): ResponseEntity<Flux<ProjectMemberViaRolesModel>>

    @GetMapping("/")
    fun getMembers(): Mono<ResponseEntity<Flux<Void>>>

    @GetMapping("/example")
    fun example(
        @RequestBody connectMembersRequest: ConnectMembersIn,
        @PathVariable projectId: String
    ): ResponseEntity<Flux<ProjectMemberModel>>
}