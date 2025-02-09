package via.easyflow.modules.project.internal.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.converter.function.converter
import via.easyflow.core.layer.converter.function.cv
import via.easyflow.core.layer.factory.ILayerConverterFactory
import via.easyflow.core.layer.manager.HashLayerConverterManager
import via.easyflow.modules.auth.model.dto.UserModelDto
import via.easyflow.modules.auth.model.internal.UserModel
import via.easyflow.modules.project.internal.entity.ProjectEntity
import via.easyflow.modules.project.model.operation.relation.ProjectModel

@Configuration
class ProjectLayerConfiguration(
    private val converterFactory: ILayerConverterFactory<LayerType, LayerType>,
) {
    private fun entityToModel(builder: HashLayerConverterManager.LayerBuilder<LayerType>) {
        builder.layer(
            LayerType.ENTITY to LayerType.MODEL
        ).transition {
            listOf(
                cv { from: ProjectEntity ->
                    ProjectModel(
                        projectId = from.projectId,
                        name = from.name,
                        description = from.description,
                        createdAt = from.createdAt,
                    )
                },
                cv { from: ProjectModel ->
                    ProjectEntity(
                        projectId = from.projectId,
                        name = from.name,
                        description = from.description,
                        createdAt = from.createdAt!!
                    )
                }
            )
        }
    }

    @Bean(name = ["projectLayerConverter"])
    fun projectLayerConverterManager(): HashLayerConverterManager<LayerType> =
        HashLayerConverterManager.builder(converterFactory)
            .fillers(this::entityToModel)
            .build()
}