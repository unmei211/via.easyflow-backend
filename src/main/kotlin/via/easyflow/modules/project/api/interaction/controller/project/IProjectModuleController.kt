package via.easyflow.modules.project.api.interaction.controller.project

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.model.ProjectDetailsModel
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.model.ProjectModel

@RestController
@RequestMapping("/module/projects")
interface IProjectModuleController {
    @PostMapping("/create")
    fun upsertProject(@RequestBody projectRequest: UpsertProjectIn): Mono<
            ResponseEntity<ProjectDetailsModel>>

    @GetMapping
    fun getProjects(): Mono<ResponseEntity<Flux<ProjectModel>>>
}