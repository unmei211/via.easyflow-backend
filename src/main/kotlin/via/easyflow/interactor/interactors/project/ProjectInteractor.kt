package via.easyflow.interactor.interactors.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.ConnectProjectMembersInteractorInput
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.interactor.interactors.project.contract.GetProjectMembersInteractorInput
import via.easyflow.interactor.usecases.cases.project.ConnectOwnerToProjectCase
import via.easyflow.interactor.usecases.cases.project.ConnectProjectMembersCase
import via.easyflow.interactor.usecases.cases.project.CreateProjectCase
import via.easyflow.interactor.usecases.cases.project.GetProjectMembersCase
import via.easyflow.interactor.usecases.cases.project.ProjectMustBeAvailableForUserCase
import via.easyflow.interactor.usecases.cases.project.UsersMustNotBeInProjectCase
import via.easyflow.interactor.usecases.cases.user.UserMustBeAvailableCase
import via.easyflow.interactor.usecases.cases.user.UsersMustBeAvailableCase
import via.easyflow.interactor.usecases.invoker.ProjectCaseInvoker
import via.easyflow.interactor.usecases.invoker.TaskCaseInvoker
import via.easyflow.interactor.usecases.invoker.UserCaseInvoker
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.model.ProjectMemberModel

@Component
class ProjectInteractor(
    // User
    private val userInvoker: UserCaseInvoker,
    // Project
    private val projectInvoker: ProjectCaseInvoker,
    // Task
    private val taskInvoker: TaskCaseInvoker
) : IProjectInteractor {
    override fun getProjectMembers(input: GetProjectMembersInteractorInput): Flux<ProjectMemberModel> {
        val userAvailableValidated: Mono<Unit> = userInvoker.invoke(
            UsersMustBeAvailableCase.Input(
                userIds = listOf(input.userId),
            )
        )

        val projectAvailableForUserValidated = projectInvoker.invoke<ProjectMustBeAvailableForUserCase, _, _>(
            input = ProjectMustBeAvailableForUserCase.Input(
                userId = input.userId,
                projectId = input.projectId
            )
        )

        return userAvailableValidated.zipWith(projectAvailableForUserValidated)
            .thenMany(
                projectInvoker.invoke(
                    GetProjectMembersCase.Input(
                        projectId = input.projectId
                    )
                )
            )
    }

    override fun connectMembers(input: aConnectProjectMembersInteractorInput): Flux<ProjectMemberModel> {
        val usersToProjectValidated: Mono<Unit> = projectInvoker.invoke(
            input = UsersMustNotBeInProjectCase.Input(
                projectId = input.projectId,
                userIds = input.userIds
            )
        )

        val usersAvailableValidated: Mono<Unit> = userInvoker.invoke(
            UsersMustBeAvailableCase.Input(
                userIds = input.userIds
            )
        )

        return usersAvailableValidated.zipWith(usersToProjectValidated)
            .thenMany(
                projectInvoker.invoke(
                    ConnectProjectMembersCase.Input(
                        projectId = input.projectId,
                        userIds = input.userIds
                    )
                )
            )
    }

    override fun createProject(input: CreateProjectInteractorInput): Mono<ProjectDetailsModel> {
        val userValidated: Mono<Unit> = userInvoker.invoke(
            UserMustBeAvailableCase.Input(
                userId = input.userId
            )
        )

        return userValidated.then(
            projectInvoker.invoke<CreateProjectCase, _, _>(
                CreateProjectCase.Input(
                    ownerId = input.userId,
                    projectName = input.projectName,
                    projectDescription = input.projectDescription,
                )
            )
        ).flatMap { (project, owner) ->
            projectInvoker.invoke<ConnectOwnerToProjectCase, _, _>(
                ConnectOwnerToProjectCase.Input(
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