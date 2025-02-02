package via.easyflow.core.layer.converter

import via.easyflow.core.layer.converter.function.TypedConverterFunction

interface IMutableLayerConverter<L1, L2> : ILayerConverter<L1, L2> {
    fun <T : Any, R : Any> addConverterFunction(
        function: TypedConverterFunction<T, R>,
    )
}