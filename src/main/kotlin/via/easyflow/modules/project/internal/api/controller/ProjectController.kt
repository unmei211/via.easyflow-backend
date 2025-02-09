package via.easyflow.modules.project.internal.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.modules.project.api.controller.IProjectController
import via.easyflow.modules.project.model.operation.details.ProjectDetailsModel
import via.easyflow.modules.project.api.service.IProjectService
import via.easyflow.modules.project.model.exchange.`in`.CreateProjectIn
import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles
import via.easyflow.modules.project.model.operation.relation.ProjectModel

@Controller
class ProjectController(
    private val projectService: IProjectService
) : IProjectController {
    override fun createProject(projectRequest: CreateProjectIn): Mono<ResponseEntity<ProjectDetailsModel>> {
        val projectOwnerPair = projectService.createProject(projectRequest)
        val response = projectOwnerPair.map {
            val owner = it.second
            val project = it.first
            ResponseEntity.ok(
                ProjectDetailsModel(
                    projectId = project.projectId,
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