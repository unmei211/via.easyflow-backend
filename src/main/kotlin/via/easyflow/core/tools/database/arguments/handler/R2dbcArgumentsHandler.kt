package via.easyflow.core.tools.database.arguments.handler

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.resolver.mapping.ArgumentValueResolverMapping
import java.util.Collections.synchronizedMap

class R2dbcArgumentsHandler(
    private val resolverManager: ArgumentValueResolverMapping
) : IArgumentsHandler {
    private val map = synchronizedMap(mutableMapOf<String, Any>())
    override fun getMap(): Map<String, Any> {
        return map
    }

    override fun bind(vararg binds: Pair<String, Any>): Mono<IArgumentsHandler> {
        val bindingFlux = Flux
            .fromArray(binds)
            .flatMap { (placeholder, value) ->
                val resolver = resolverManager.get(placeholder)
                resolver.resolve(value).map { resolverValue ->
                    placeholder to resolverValue
                }
            }
            .map { (placeholder, value) ->
                map[placeholder] = value
            }
        return bindingFlux.then(Mono.just(this))
    }

    override fun bindAsync(bind: Mono<Pair<String, Any>>): Mono<IArgumentsHandler> {
        return bind.flatMap {
            val resolver = resolverManager.get(it.first)
            resolver.resolve(it.second)
                .map { resolverValue ->
                    map[it.first] = resolverValue
                }
        }.then(Mono.just(this))
    }
}