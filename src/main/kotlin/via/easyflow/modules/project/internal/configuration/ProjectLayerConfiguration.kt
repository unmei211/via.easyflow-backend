package via.easyflow.modules.project.internal.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.converter.function.cv
import via.easyflow.core.layer.factory.ILayerConverterFactory
import via.easyflow.core.layer.manager.HashLayerConverterManager
import via.easyflow.shared.modules.project.model.ProjectMemberModel
import via.easyflow.shared.modules.project.model.ProjectModel
import via.easyflow.modules.project.repository.member.model.ProjectMemberEntity

@Configuration
class ProjectLayerConfiguration(
    private val converterFactory: ILayerConverterFactory<LayerType, LayerType>,
) {
    private fun serviceToRepository(builder: HashLayerConverterManager.LayerBuilder<LayerType>) {
        builder.layer(
            LayerType.ENTITY to LayerType.MODEL
        ).transition {
            listOf(
                cv { from: ProjectMemberEntity ->
                    ProjectMemberModel(
                        memberId = from.projectMemberId,
                        projectId = from.projectId,
                        userId = from.userId,
                        joinedAt = from.joinedAt,
                    )
                },
                cv { from: via.easyflow.modules.project.repository.project.model.ProjectEntity ->
                    ProjectModel(
                        projectId = from.projectId,
                        name = from.name,
                        description = from.description,
                        createdAt = from.createdAt,
                    )
                },
                cv { from: ProjectModel ->
                    via.easyflow.modules.project.repository.project.model.ProjectEntity(
                        projectId = from.projectId!!,
                        name = from.name,
                        description = from.description,
                        createdAt = from.createdAt!!
                    )
                },
                cv { from: ProjectMemberModel ->
                    ProjectMemberEntity(
                        projectMemberId = from.memberId,
                        projectId = from.projectId,
                        userId = from.userId,
                        joinedAt = from.joinedAt
                    )
                }
            )
        }
    }

    @Bean(name = ["projectLayerConverter"])
    fun projectLayerConverterManager(): HashLayerConverterManager<LayerType> =
        HashLayerConverterManager.builder(converterFactory)
            .fillers(this::serviceToRepository)
            .build()
}