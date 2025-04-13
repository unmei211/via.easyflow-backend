package via.easyflow.modules.project.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.api.service.IProjectService
import via.easyflow.shared.modules.project.api.inputs.project.UpsertProjectIn
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.shared.modules.project.api.controller.IProjectModuleController

@Controller
class ProjectModuleController(
    private val projectService: IProjectService
) : IProjectModuleController {
    override fun upsertProject(projectRequest: UpsertProjectIn): Mono<ResponseEntity<ProjectDetailsModel>> {
        val projectOwnerPair = projectService.upsertProject(projectRequest)
        val response = projectOwnerPair.map {
            val owner = it.second
            val project = it.first
            ResponseEntity.ok(
                ProjectDetailsModel(
                    projectId = project.projectId!!,
                    owner = owner,
                    name = project.name,
                    description = project.description,
                    createdAt = project.createdAt!!,
                    members = emptyList()
                )
            )
        }
        return response
    }

    override fun getProjects(): Mono<ResponseEntity<Flux<ProjectModel>>> {
        val projectsFlux = projectService.getProjects()
        val responseMono = Mono.just(ResponseEntity.ok(projectsFlux))
        return responseMono
    }
}