package via.easyflow.modules.project.api.service.project

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.models.`in`.project.UpsertProjectIn
import via.easyflow.modules.project.api.models.model.ProjectModel
import via.easyflow.modules.project.api.models.model.ProjectOwnerModel
import via.easyflow.modules.project.internal.entity.ProjectEntity
import via.easyflow.modules.project.internal.entity.ProjectOwnerEntity
import via.easyflow.modules.project.internal.repository.member.MemberRepository
import via.easyflow.modules.project.internal.repository.project.IProjectRepository
import java.time.Instant

@Service
class ProjectService(
    private val projectRepository: IProjectRepository,
    private val memberRepository: MemberRepository,
    @Qualifier("projectLayerConverter") private val converterManager: IEnumerableLayerConverterManager<LayerType>
) : IProjectService {
    private val entityModelConverter =
        converterManager.getLayerConverter(LayerType.ENTITY to LayerType.MODEL)

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
                it.createdAt = Instant.now()
                it
            }
            .map { entityModelConverter.convert(it to ProjectEntity::class) }

        val changeOwnerEntityMono = Mono
            .just(ownerId)
            .map { ProjectOwnerEntity(userId = it, projectId = project.projectId!!) }

        val dataPair = Mono.zip(projectEntityMono, changeOwnerEntityMono)

        val upsertProjectVoid = dataPair
            .map { it.t1 }
            .flatMap { projectRepository.upsertProject(it) }

        val changeOwnerVoid = dataPair.map { it.t2 }.flatMap { projectRepository.changeProjectOwner(it) }
        return Mono.`when`(upsertProjectVoid, changeOwnerVoid)
            .then(Mono.fromCallable { project to ProjectOwnerModel(userId = ownerId, projectId = project.projectId!!) })
    }
}