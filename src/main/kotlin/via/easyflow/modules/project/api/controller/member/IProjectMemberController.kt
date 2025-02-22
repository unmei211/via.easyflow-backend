package via.easyflow.modules.project.api.controller.member

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.`in`.member.ConnectMembersIn

@RestController
@RequestMapping("/projects/{projectId}/members")
interface IProjectMemberController {
    @PostMapping("/connect")
    fun connectMembers(
        @RequestBody connectMembersRequest: ConnectMembersIn,
        @PathVariable projectId: String
    ): Mono<
            ResponseEntity<Void>>

    @GetMapping("/")
    fun getMembers(): Mono<ResponseEntity<Flux<Void>>>
}