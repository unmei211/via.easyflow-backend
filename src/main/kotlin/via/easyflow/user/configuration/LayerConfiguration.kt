package via.easyflow.user.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.shared.layer.LayerType
import via.easyflow.shared.layer.converter.function.converter
import via.easyflow.shared.layer.factory.ILayerConverterFactory
import via.easyflow.shared.layer.manager.HashLayerConverterManager
import via.easyflow.user.model.dto.UserModelDto
import via.easyflow.user.model.internal.UserModel

@Configuration
class LayerConfiguration(
    private val converterFactory: ILayerConverterFactory<LayerType, LayerType>,
) {
    private fun modelToDtoFiller(builder: HashLayerConverterManager.LayerBuilder<LayerType>) {
        builder.layer(
            LayerType.MODEL to LayerType.DTO
        ).transition {
            listOf(
                converter { from: UserModel ->
                    UserModelDto(
                        id = from.email,
                        email = from.email,
                        password = from.password
                    )
                },
            )
        }
    }

    @Bean
    fun layerConverterManager(): HashLayerConverterManager<LayerType> =
        HashLayerConverterManager.LayerBuilder(converterFactory)
            .fillers(this::modelToDtoFiller)
            .build()
}