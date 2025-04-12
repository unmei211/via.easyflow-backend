package via.easyflow.interactor.usecases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.interaction.service.project.IProjectService
import via.easyflow.modules.project.api.model.ProjectModel
import via.easyflow.modules.project.api.model.ProjectOwnerModel

@Component
class CreateProjectCase(
    private val projectService: IProjectService,
) : UseCase<CreateProjectCaseInput, Mono<Pair<ProjectModel, ProjectOwnerModel>>> {
    override fun invoke(input: CreateProjectCaseInput): Mono<Pair<ProjectModel, ProjectOwnerModel>> {
        val result: Mono<Pair<ProjectModel, ProjectOwnerModel>> = projectService.upsertProject(
            upsertRequest = UpsertProjectIn(
                project = ProjectModel(
                    name = input.projectName,
                    description = input.projectDescription
                ),
                ownerId = input.ownerId,
            )
        )
        return result
    }
}


data class CreateProjectCaseInput(
    val ownerId: String,
    val projectName: String,
    val projectDescription: String
)