package via.easyflow.core.tools.database

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.json.io.ReactiveJsonIO
import kotlin.reflect.KClass

class R2dbcJsonbMapper(
    private val columnName: String,
    private val jsonRIO: ReactiveJsonIO
) : R2dbcMapper {
    override fun <T : Any> flatMap(result: io.r2dbc.spi.Result, clazz: KClass<T>): Mono<T> {
        return Mono
            .from(result.map { row -> row.get(columnName, String::class.java)!! })
            .flatMap { jsonRIO.fromJson(it, clazz) }
    }

    override fun <T : Any> flatMapMany(result: io.r2dbc.spi.Result, clazz: KClass<T>): Flux<T> {
        TODO()
    }
}