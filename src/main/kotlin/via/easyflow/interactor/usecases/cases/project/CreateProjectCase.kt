package via.easyflow.interactor.usecases.cases.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.cases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.shared.modules.project.api.inputs.project.UpsertProjectIn
import via.easyflow.shared.modules.project.api.service.IProjectService
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.shared.modules.project.model.ProjectOwnerModel
import kotlin.reflect.KClass

@Component
@Case(CaseScope.PROJECT)
class CreateProjectCase(
    private val projectService: IProjectService,
) : TypedUseCase<CreateProjectCase.Input, Mono<Pair<ProjectModel, ProjectOwnerModel>>> {
    override fun getInputType(): KClass<Input> {
        return Input::class
    }

    override fun invoke(input: Input): Mono<Pair<ProjectModel, ProjectOwnerModel>> {
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

    data class Input(
        val ownerId: String,
        val projectName: String,
        val projectDescription: String
    )
}
