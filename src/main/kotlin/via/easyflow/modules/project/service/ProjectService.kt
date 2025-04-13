package via.easyflow.modules.project.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.shared.modules.project.api.inputs.project.UpsertProjectIn
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.shared.modules.project.model.ProjectOwnerModel
import via.easyflow.modules.project.repository.project.model.ProjectEntity
import via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity
import via.easyflow.modules.project.repository.member.MemberRepository
import via.easyflow.modules.project.repository.project.IProjectRepository
import via.easyflow.shared.modules.project.api.service.IProjectService
import java.time.LocalDateTime

@Service
class ProjectService(
    private val projectRepository: via.easyflow.modules.project.repository.project.IProjectRepository,
    private val memberRepository: via.easyflow.modules.project.repository.member.MemberRepository,
    @Qualifier("projectLayerConverter") private val converterManager: IEnumerableLayerConverterManager<LayerType>
) : IProjectService {
    private val entityModelConverter =
        converterManager.getLayerConverter(LayerType.ENTITY to LayerType.MODEL)

    override fun projectIsExists(projectId: String): Mono<Boolean> {
        return projectRepository.existsProjectById(projectId)
    }

    override fun getProjects(): Flux<ProjectModel> {
        return projectRepository
            .getProjects()
            .map {
                entityModelConverter.convert(it to ProjectModel::class)
            }
    }

    override fun upsertProject(upsertRequest: UpsertProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>> {
        val ownerId = upsertRequest.ownerId
        val project = upsertRequest.project
        project.projectId = project.projectId ?: uuid()

        val projectEntityMono = Mono.just(project)
            .map {
                it.createdAt = LocalDateTime.now()
                it
            }
            .map { entityModelConverter.convert(it to via.easyflow.modules.project.repository.project.model.ProjectEntity::class) }

        val changeOwnerEntityMono = Mono
            .just(ownerId)
            .map {
                via.easyflow.modules.project.repository.project.model.ProjectOwnerEntity(
                    userId = it,
                    projectId = project.projectId!!
                )
            }

        val dataPair = Mono.zip(projectEntityMono, changeOwnerEntityMono)

        val upsertProjectVoid = dataPair
            .map { it.t1 }
            .flatMap { projectRepository.upsertProject(it) }

        val changeOwnerVoid = dataPair.map { it.t2 }.flatMap { projectRepository.changeProjectOwner(it) }
        return Mono.`when`(upsertProjectVoid, changeOwnerVoid)
            .then(Mono.fromCallable { project to ProjectOwnerModel(userId = ownerId, projectId = project.projectId!!) })
    }
}