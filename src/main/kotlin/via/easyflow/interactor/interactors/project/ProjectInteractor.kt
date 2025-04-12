package via.easyflow.interactor.interactors.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.interactor.usecases.project.ConnectOwnerToProjectCase
import via.easyflow.interactor.usecases.project.ConnectOwnerToProjectCaseInput
import via.easyflow.interactor.usecases.project.CreateProjectCase
import via.easyflow.interactor.usecases.project.CreateProjectCaseInput
import via.easyflow.interactor.usecases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UserMustBeAvailableUseCaseInput
import via.easyflow.modules.project.api.model.ProjectDetailsModel

@Component
class ProjectInteractor(
    private val userIsAvailableCase: UserMustBeAvailableCase,
    private val createProjectCase: CreateProjectCase,
    private val connectOwnerToProjectCase: ConnectOwnerToProjectCase
) : IProjectInteractor {
    override fun createProject(input: CreateProjectInteractorInput): Mono<ProjectDetailsModel> {
        val userValidated = userIsAvailableCase.invoke(
            UserMustBeAvailableUseCaseInput(
                userId = input.userId
            )
        )

        return userValidated.then(
            createProjectCase.invoke(
                CreateProjectCaseInput(
                    ownerId = input.userId,
                    projectName = input.projectName,
                    projectDescription = input.projectDescription,
                )
            )
        ).flatMap { (project, owner) ->
            connectOwnerToProjectCase.invoke(
                ConnectOwnerToProjectCaseInput(
                    projectId = project.projectId ?: "",
                    userId = owner.userId,
                )
            ).map { projectMember ->
                ProjectDetailsModel(
                    projectId = projectMember.projectId,
                    name = project.name,
                    description = project.description,
                    createdAt = project.createdAt!!,
                    members = listOf(projectMember),
                    owner = owner
                )
            }
        }
    }
}