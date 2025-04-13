package via.easyflow.interactor.interactors.project

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.ConnectProjectMembersInteractorInput
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.interactor.interactors.project.contract.GetProjectMembersInteractorInput
import via.easyflow.modules.project.api.model.ProjectDetailsModel
import via.easyflow.modules.project.api.model.ProjectMemberModel

interface IProjectInteractor {
    fun createProject(input: CreateProjectInteractorInput): Mono<ProjectDetailsModel>
    fun connectMembers(input: ConnectProjectMembersInteractorInput): Flux<ProjectMemberModel>
    fun getProjectMembers(input: GetProjectMembersInteractorInput): Flux<ProjectMemberModel>
}