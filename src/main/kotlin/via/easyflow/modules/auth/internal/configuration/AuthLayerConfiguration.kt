package via.easyflow.modules.auth.internal.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.converter.function.converter
import via.easyflow.core.layer.factory.ILayerConverterFactory
import via.easyflow.core.layer.manager.HashLayerConverterManager
import via.easyflow.modules.auth.model.dto.UserModelDto
import via.easyflow.modules.auth.model.internal.UserModel

@Configuration
class AuthLayerConfiguration(
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

    @Bean(name = ["authLayerConverter"])
    fun authLayerConverterManager(): HashLayerConverterManager<LayerType> =
        HashLayerConverterManager.builder(converterFactory)
            .fillers(this::modelToDtoFiller)
            .build()
}