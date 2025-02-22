package via.easyflow.modules.project.api.controller.project

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.models.model.ProjectDetailsModel
import via.easyflow.modules.project.api.service.project.IProjectService
import via.easyflow.modules.project.api.models.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.models.model.ProjectModel

@Controller
class ProjectController(
    private val projectService: IProjectService
) : IProjectController {
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