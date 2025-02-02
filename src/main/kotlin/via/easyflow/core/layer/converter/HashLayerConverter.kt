package via.easyflow.core.layer.converter

import via.easyflow.core.layer.exception.ConvertException
import via.easyflow.core.layer.converter.function.TypedConverterFunction
import via.easyflow.core.logger.logger
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class HashLayerConverter<L1, L2>(
    private val layerTransition: Pair<L1, L2>,
    private val transitions: HashMap<
            Pair<KClass<*>, KClass<*>>, TypedConverterFunction<*, *>>,
) : IMutableLayerConverter<L1, L2> {
    private val log = logger()

    override fun getLayerTransition(): Pair<L1, L2> {
        return layerTransition
    }

    override fun <T : Any, R : Any> addConverterFunction(
        function: TypedConverterFunction<T, R>
    ) {
        transitions[Pair(function.fromClass, function.toClass)] = function
    }

    override fun <T : Any, R : Any> convert(from: T, to: KClass<R>): R {
        try {
            val convertFunction = transitions[Pair(from::class, to)] as TypedConverterFunction<T, R>
            return convertFunction.convert(from)
        } catch (e: Exception) {
            log.warn("Can't convert from ${from::class} to ${to::class}: ${e.message}")
            throw ConvertException(e.message, e.cause)
        }
    }

    inline fun <T : Any, reified R : Any> convert(from: T): R = convert(from, R::class)
}