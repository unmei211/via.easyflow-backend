package via.easyflow.core.layer.converter.function

import kotlin.reflect.KClass

class TypedConverterFunction<T : Any, R : Any>(
    val fromClass: KClass<T>,
    val toClass: KClass<R>,
    private val function: (T) -> R
) {
    fun convert(from: T): R = function(from)
}
