package via.easyflow.interactor.controller.project

import java.time.Duration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.controller.project.model.ConnectMembersRequestBody
import via.easyflow.interactor.interactors.project.IProjectInteractor
import via.easyflow.interactor.interactors.project.contract.ConnectProjectMembersInteractorInput
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.interactor.interactors.project.contract.GetProjectMembersInteractorInput
import via.easyflow.shared.modules.project.model.ProjectDetailsModel
import via.easyflow.shared.modules.project.model.ProjectMemberModel

@Controller
class ProjectController(
    private val projectInteractor: IProjectInteractor
) : IProjectController {
    override fun getProjectMembers(projectId: String, userId: String): Mono<ResponseEntity<Flux<ProjectMemberModel>>> {
        return Mono.just(
            ResponseEntity.ok(
                projectInteractor.getProjectMembers(
                    input = GetProjectMembersInteractorInput(
                        projectId = projectId,
                        userId = userId
                    )
                )
            )
        )
    }

    override fun connectMembersToProject(
        connectMembersRequest: ConnectMembersRequestBody,
        projectId: String
    ): Mono<ResponseEntity<Flux<ProjectMemberModel>>> {
        return Mono.just(
            ResponseEntity.ok(
                projectInteractor.connectMembers(
                    ConnectProjectMembersInteractorInput(
                        projectId = projectId,
                        userIds = connectMembersRequest.userIds
                    )
                )
            )
        )
    }

    override fun upsertProject(input: CreateProjectInteractorInput): Mono<ResponseEntity<Mono<ProjectDetailsModel>>> {
        val projectMono: Mono<ProjectDetailsModel> = projectInteractor.createProject(input)
        return Mono.just(ResponseEntity.ok(projectMono))
    }
}