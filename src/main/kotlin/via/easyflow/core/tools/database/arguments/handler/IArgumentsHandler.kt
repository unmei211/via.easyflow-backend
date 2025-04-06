package via.easyflow.core.tools.database.arguments.handler

import reactor.core.publisher.Mono

interface IArgumentsHandler {
    fun bind(
        vararg binds: Pair<String, Any>,
    ): Mono<IArgumentsHandler>

    fun bindAsync(bind: Mono<Pair<String, Any>>): Mono<IArgumentsHandler>

    fun getMap(): Map<String, Any>
}