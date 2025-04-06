package via.easyflow.core.tools.database.arguments.handler

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SimpleArgumentsHandler(
) : IArgumentsHandler {
    private val argsMap = mutableMapOf<String, Any>()
    override fun getMap(): Map<String, Any> {
        return argsMap
    }

    override fun bind(vararg binds: Pair<String, Any>): Mono<IArgumentsHandler> {
        binds.forEach {
            argsMap[it.first] = it.second
        }
        return Mono.just(this)
    }

    override fun bindAsync(bind: Mono<Pair<String, Any>>): Mono<IArgumentsHandler> {
        return bind.map {
            argsMap[it.first] = it.second
        }.map { this }
    }
}