package via.easyflow.modules.project.api.service.role

import org.springframework.beans.factory.annotation.Qualifier
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.modules.project.api.models.`in`.project.AddRoleIn
import via.easyflow.modules.project.api.models.`in`.role.ChangeDefaultRoleIn
import via.easyflow.modules.project.api.models.model.ProjectDefaultRoleModel
import via.easyflow.modules.project.api.models.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.internal.entity.ProjectDefaultRoleEntity
import via.easyflow.modules.project.internal.repository.role.RoleRepository

class ProjectRoleService(
    private val roleRepository: RoleRepository,
    @Qualifier("projectLayerConverter") private val converterManager: IEnumerableLayerConverterManager<LayerType>
) : IProjectRoleService {
    private val modelEntityConverter = converterManager.getLayerConverter(LayerType.MODEL to LayerType.ENTITY)
    override fun addRole(addRoleIn: AddRoleIn): Mono<ProjectMemberViaRolesModel> {
        TODO("Not yet implemented")
    }

    override fun changeDefaultRole(defaultRoleIn: ChangeDefaultRoleIn): Mono<ProjectDefaultRoleModel> {
        val defaultRoleModel = ProjectDefaultRoleModel(
            defaultRoleId = uuid(),
            projectId = defaultRoleIn.projectId,
            projectRoleId = defaultRoleIn.projectRoleId,
        )
        val changedDefaultRoleEntity =
            roleRepository.changeDefaultRole(modelEntityConverter.convert(defaultRoleModel to ProjectDefaultRoleEntity::class))

        return changedDefaultRoleEntity.map { modelEntityConverter.convert(it to ProjectDefaultRoleModel::class) }
    }
}