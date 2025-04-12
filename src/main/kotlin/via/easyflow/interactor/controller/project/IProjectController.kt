package via.easyflow.interactor.controller.project

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.modules.project.api.model.ProjectDetailsModel


@RestController
@RequestMapping("/interactor/project", produces = [MediaType.APPLICATION_NDJSON_VALUE])
interface IProjectController {
    @PostMapping("/upsert")
    fun upsertProject(@RequestBody input: CreateProjectInteractorInput): Mono<
            ResponseEntity<ProjectDetailsModel>>
}