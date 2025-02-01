package via.easyflow.shared.layer.factory

import via.easyflow.shared.layer.converter.IMutableLayerConverter
import via.easyflow.shared.layer.converter.function.TypedConverterFunction
import kotlin.reflect.KClass

interface ILayerConverterFactory<L1, L2> {
    fun createConverter(
        layerFirst: L1,
        layerSecond: L2,
        transitions: HashMap<Pair<KClass<*>, KClass<*>>, TypedConverterFunction<*, *>> = HashMap()
    ): IMutableLayerConverter<L1, L2>
}