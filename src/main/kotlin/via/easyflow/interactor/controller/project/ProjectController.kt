package via.easyflow.interactor.controller.project

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.IProjectInteractor
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.modules.project.api.model.ProjectDetailsModel

@Controller
class ProjectController(
    private val projectInteractor: IProjectInteractor
) : IProjectController {
    override fun upsertProject(input: CreateProjectInteractorInput): Mono<ResponseEntity<ProjectDetailsModel>> {
        projectInteractor.createProject(input)
    }
}