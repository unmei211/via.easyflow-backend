package via.easyflow.core.layer.factory

import via.easyflow.core.layer.converter.HashLayerConverter
import via.easyflow.core.layer.converter.function.TypedConverterFunction
import kotlin.reflect.KClass

class EnumerableLayerConverterFactory<E : Enum<*>> : ILayerConverterFactory<E, E> {

    override fun createConverter(
        layerFirst: E,
        layerSecond: E,
        transitions: HashMap<Pair<KClass<*>, KClass<*>>, TypedConverterFunction<*, *>>
    ): HashLayerConverter<E, E> {
        val converter = HashLayerConverter(
            layerTransition = Pair(layerFirst, layerSecond),
            transitions = transitions
        )
        return converter
    }
}