package via.easyflow.core.layer.converter

import kotlin.reflect.KClass

interface ILayerConverter<L1, L2> {
    fun <T : Any, R : Any> convert(from: T, to: KClass<R>): R
    fun <T : Any, R : Any> convert(fromTo: Pair<T, KClass<R>>): R
    fun getLayerTransition(): Pair<L1, L2>
}