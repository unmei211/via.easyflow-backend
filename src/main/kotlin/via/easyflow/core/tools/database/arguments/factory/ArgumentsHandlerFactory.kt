package via.easyflow.core.tools.database.arguments.factory

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.handler.R2dbcArgumentsHandler
import via.easyflow.core.tools.database.arguments.handler.SimpleArgumentsHandler
import via.easyflow.core.tools.database.arguments.resolver.ByDefaultValueResolver
import via.easyflow.core.tools.database.arguments.resolver.DocumentValueResolver
import via.easyflow.core.tools.database.arguments.resolver.mapping.ArgumentValueResolverMapping
import via.easyflow.core.tools.json.io.ReactiveJsonIO

@Component
class ArgumentsHandlerFactory(
    private val mapper: ReactiveJsonIO,
    private val defaultValueResolver: ByDefaultValueResolver,
    private val documentValueResolver: DocumentValueResolver,
) : IArgumentsHandlerFactory {
    private val resolverManager = ArgumentValueResolverMapping(
        resolversMapping = mapOf(
            "document" to documentValueResolver,
        ),
        default = defaultValueResolver,
    )

    override fun getSimple(): SimpleArgumentsHandler = SimpleArgumentsHandler()
    override fun getSimpleAsync(): Mono<SimpleArgumentsHandler> =
        Mono.just(SimpleArgumentsHandler())


    override fun getR2dbc(): R2dbcArgumentsHandler {
        return R2dbcArgumentsHandler(
            resolverManager = resolverManager
        )
    }

    override fun getR2dbcAsync(): Mono<R2dbcArgumentsHandler> {
        return Mono.just(
            R2dbcArgumentsHandler(
                resolverManager = resolverManager
            )
        )
    }
}