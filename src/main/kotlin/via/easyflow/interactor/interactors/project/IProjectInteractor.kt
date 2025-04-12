package via.easyflow.interactor.interactors.project

import reactor.core.publisher.Mono
import via.easyflow.interactor.interactors.project.contract.CreateProjectInteractorInput
import via.easyflow.modules.project.api.model.ProjectDetailsModel

interface IProjectInteractor {
    fun createProject(input: CreateProjectInteractorInput): Mono<ProjectDetailsModel>
}