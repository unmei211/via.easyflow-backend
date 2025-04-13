package via.easyflow.shared.modules.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.api.inputs.project.UpsertProjectIn
import via.easyflow.shared.modules.project.model.ProjectModel

@RestController
@RequestMapping("/module/projects")
interface IProjectModuleController {
    @PostMapping("/create")
    fun upsertProject(@RequestBody projectRequest: UpsertProjectIn): Mono<
            ResponseEntity<ProjectDetailsModel>>

    @GetMapping
    fun getProjects(): Mono<ResponseEntity<Flux<ProjectModel>>>
}