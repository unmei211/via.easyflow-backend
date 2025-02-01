package via.easyflow.shared.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.shared.layer.LayerType
import via.easyflow.shared.layer.factory.EnumerableLayerConverterFactory
import via.easyflow.shared.layer.factory.ILayerConverterFactory

@Configuration
class LayerConfiguration {
    @Bean
    fun layerConverterFactory(): ILayerConverterFactory<LayerType, LayerType> {
        return EnumerableLayerConverterFactory()
    }
}