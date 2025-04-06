package via.easyflow.core.tools.database.mapper

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

interface R2dbcMapper {
    fun <T : Any> flatMap(result: io.r2dbc.spi.Result, clazz: KClass<T>): Mono<T>
    fun <T : Any> flatMapMany(result: io.r2dbc.spi.Result, clazz: KClass<T>): Flux<T>
}