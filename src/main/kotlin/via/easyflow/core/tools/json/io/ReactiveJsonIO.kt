package via.easyflow.core.tools.json.io

import reactor.core.publisher.Mono
import kotlin.reflect.KClass

interface ReactiveJsonIO {
    fun <T : Any> toJson(obj: T): Mono<String>
    fun <T : Any> toJson(objMono: Mono<T>): Mono<String>

    fun <T : Any> fromJson(json: String, clazz: KClass<T>): Mono<T>
    fun <T : Any> fromJson(json: Mono<String>, clazz: KClass<T>): Mono<T>
}