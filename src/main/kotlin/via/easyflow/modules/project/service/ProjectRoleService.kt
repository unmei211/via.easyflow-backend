package via.easyflow.modules.project.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.IEnumerableLayerConverterManager
import via.easyflow.core.tools.uuid.uuid
import via.easyflow.shared.modules.project.api.inputs.project.AddRoleIn
import via.easyflow.shared.modules.project.api.inputs.role.ChangeDefaultRoleIn
import via.easyflow.shared.modules.project.model.ProjectDefaultRoleModel
import via.easyflow.shared.modules.project.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.repository.role.model.ProjectDefaultRoleEntity
import via.easyflow.shared.modules.project.api.service.IProjectRoleService

@Component
class ProjectRoleService(
    private val roleRepository: via.easyflow.modules.project.repository.role.RoleRepository,
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