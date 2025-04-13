package via.easyflow.interactor.interactors.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.ConnectProjectMembersInteractorInput
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.interactor.interactors.project.contract.GetProjectMembersInteractorInput
import via.easyflow.interactor.usecases.project.*
import via.easyflow.interactor.usecases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UserMustBeAvailableUseCaseInput
import via.easyflow.interactor.usecases.user.UsersMustBeAvailableCase
import via.easyflow.interactor.usecases.user.UsersMustBeAvailableCaseInput
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.model.ProjectMemberModel

@Component
class ProjectInteractor(
    private val userIsAvailableCase: UserMustBeAvailableCase,
    private val usersMustBeAvailableCase: UsersMustBeAvailableCase,
    private val usersMustNotBeInProjectCase: UsersMustNotBeInProjectCase,

    private val createProjectCase: CreateProjectCase,
    private val connectOwnerToProjectCase: ConnectOwnerToProjectCase,
    private val connectProjectMembersCase: ConnectProjectMembersCase,
    private val getProjectMembersCase: GetProjectMembersCase,
    private val projectMustBeAvailableForUserCase: ProjectMustBeAvailableForUserCase
) : IProjectInteractor {
    override fun getProjectMembers(input: GetProjectMembersInteractorInput): Flux<ProjectMemberModel> {
        val userAvailableValidated = usersMustBeAvailableCase.invoke(
            UsersMustBeAvailableCaseInput(
                userIds = listOf(input.userId),
            )
        )

        val projectAvailableForUserValidated = projectMustBeAvailableForUserCase.invoke(
            input = ProjectIsAvailableForUserCaseInput(
                userId = input.userId,
                projectId = input.projectId
            )
        )

        return userAvailableValidated.zipWith(projectAvailableForUserValidated)
            .thenMany(
                getProjectMembersCase.invoke(
                    GetProjectMembersCaseInput(
                        projectId = input.projectId
                    )
                )
            )
    }

    override fun connectMembers(input: ConnectProjectMembersInteractorInput): Flux<ProjectMemberModel> {
        val usersToProjectValidated = usersMustNotBeInProjectCase.invoke(
            input = UsersNotInProjectCaseInput(
                projectId = input.projectId,
                userIds = input.userIds
            )
        )

        val usersAvailableValidated = usersMustBeAvailableCase.invoke(
            UsersMustBeAvailableCaseInput(
                userIds = input.userIds
            )
        )

        return usersAvailableValidated.zipWith(usersToProjectValidated)
            .thenMany(
                connectProjectMembersCase.invoke(
                    ConnectProjectMembersCaseInput(
                        projectId = input.projectId,
                        userIds = input.userIds
                    )
                )
            )
    }

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