package via.easyflow.core.tools.json.binder

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import via.easyflow.core.tools.json.io.ReactiveJsonIO

@Component
class ReactiveJsonBinder(
    private val objectMapper: ObjectMapper,
) : ReactiveJsonIO {
    override fun <T : Any> toJson(obj: T): Mono<String> =
        Mono.just(objectMapper.writeValueAsString(obj)).subscribeOn(Schedulers.boundedElastic())

    override fun <T : Any> toJson(objMono: Mono<T>): Mono<String> =
        objMono.map { objectMapper.writeValueAsString(it) }.subscribeOn(Schedulers.boundedElastic())

    override fun <T : Any> fromJson(json: String, clazz: Class<T>): Mono<T> =
        Mono.just(objectMapper.readValue(json, clazz)).subscribeOn(Schedulers.boundedElastic())


    override fun <T : Any> fromJson(json: Mono<String>, clazz: Class<T>): Mono<T> =
        json.map { objectMapper.readValue(it, clazz) }.subscribeOn(Schedulers.boundedElastic())
}