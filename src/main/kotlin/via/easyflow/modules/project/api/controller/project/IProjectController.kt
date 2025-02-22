package via.easyflow.modules.project.api.controller.project

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.model.ProjectDetailsModel
import via.easyflow.modules.project.api.models.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.models.model.ProjectModel

@RestController
@RequestMapping("/projects")
interface IProjectController {
    @PostMapping("/create")
    fun upsertProject(@RequestBody projectRequest: UpsertProjectIn): Mono<
            ResponseEntity<ProjectDetailsModel>>

    @GetMapping
    fun getProjects(): Mono<ResponseEntity<Flux<ProjectModel>>>
}