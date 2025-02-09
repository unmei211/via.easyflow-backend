package via.easyflow.modules.project.internal.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.converter.HashLayerConverter
import via.easyflow.core.layer.converter.ILayerConverter
import via.easyflow.core.layer.manager.HashLayerConverterManager
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.service.IProjectService
import via.easyflow.modules.project.internal.entity.ProjectEntity
import via.easyflow.modules.project.internal.entity.ProjectOwnerEntity
import via.easyflow.modules.project.internal.repository.IProjectRepository
import via.easyflow.modules.project.model.exchange.`in`.AddRoleIn
import via.easyflow.modules.project.model.exchange.`in`.ConnectMembersIn
import via.easyflow.modules.project.model.exchange.`in`.CreateProjectIn
import via.easyflow.modules.project.model.exchange.`in`.UpdateProjectIn
import via.easyflow.modules.project.model.operation.relation.ProjectModel
import via.easyflow.modules.project.model.operation.relation.ProjectOwnerModel
import via.easyflow.modules.project.model.operation.details.ProjectMemberViaRoles
import java.time.Instant

@Service
class ProjectService(
    private val projectRepository: IProjectRepository,
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

    override fun createProject(createRequest: CreateProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>> {
        val ownerId = createRequest.ownerId
        val project = createRequest.project
        project.projectId = uuid()

        val projectEntityMono = Mono.just(project)
            .map {
                it.createdAt = Instant.now()
                it
            }
            .map { entityModelConverter.convert(it to ProjectEntity::class) }
        val changeOwnerEntityMono = Mono
            .just(ownerId)
            .map { ProjectOwnerEntity(userId = it, projectId = project.projectId) }

        val dataPair = Mono.zip(projectEntityMono, changeOwnerEntityMono)

        val upsertProjectVoid = dataPair
            .map { it.t1 }
            .flatMap { projectRepository.upsertProject(it) }

        val changeOwnerVoid = dataPair.map { it.t2 }.flatMap { projectRepository.changeProjectOwner(it) }
        return Mono.`when`(upsertProjectVoid, changeOwnerVoid)
            .then(Mono.fromCallable { project to ProjectOwnerModel(userId = ownerId, projectId = project.projectId) })
    }

    override fun updateProject(updateRequest: UpdateProjectIn): Mono<Pair<ProjectModel, ProjectOwnerModel>> {
        TODO("Not yet implemented")
    }

    override fun connectMembers(connectMembersRequest: ConnectMembersIn): Flux<ProjectMemberViaRoles> {
        TODO("Not yet implemented")
    }

    override fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRoles> {
        TODO("Not yet implemented")
    }
}