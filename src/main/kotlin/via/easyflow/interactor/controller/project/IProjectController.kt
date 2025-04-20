package via.easyflow.interactor.controller.project

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.project.model.ConnectMembersRequestBody
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.model.ProjectMemberModel


@RestController
@RequestMapping("/interactor/project", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IProjectController {

    @PostMapping("/{projectId}/members/connect")
    fun connectMembersToProject(
        @RequestBody connectMembersRequest: ConnectMembersRequestBody,
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