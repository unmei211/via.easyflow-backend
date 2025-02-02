package via.easyflow.core.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.factory.EnumerableLayerConverterFactory
import via.easyflow.core.layer.factory.ILayerConverterFactory

@Configuration
class GlobalLayerConfiguration {
    @Bean
    fun layerConverterFactory(): ILayerConverterFactory<LayerType, LayerType> {
        return EnumerableLayerConverterFactory()
    }
}