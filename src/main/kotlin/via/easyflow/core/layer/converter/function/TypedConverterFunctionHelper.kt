package via.easyflow.core.layer.converter.function


inline fun <reified T : Any, reified R : Any> converter(
    noinline function: (T) -> R
): TypedConverterFunction<T, R> {
    return TypedConverterFunction(T::class, R::class, function)
}