package via.easyflow.interactor.controller.project

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.shared.modules.project.api.inputs.member.ConnectMembersIn
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.model.ProjectMemberModel


@RestController
@RequestMapping("/interactor/project", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IProjectController {

    @PostMapping("/{projectId}/members/connect")
    fun connectMembersToProject(
        @RequestBody connectMembersRequest: ConnectMembersIn,
        @PathVariable projectId: String
    ): Mono<ResponseEntity<Flux<ProjectMemberModel>>>

    @PostMapping("/upsert")
    fun upsertProject(@RequestBody input: CreateProjectInteractorInput): Mono<
            ResponseEntity<Mono<ProjectDetailsModel>>>

    @GetMapping("/{projectId}/members")
    fun getProjectMembers(
        @PathVariable projectId: String,
        @RequestParam(required = true) userId: String
    ): Mono<ResponseEntity<Flux<ProjectMemberModel>>>
}