package via.easyflow.core.tools.database.arguments.handler

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono

interface IArgumentsHandler {
    fun bind(
        vararg binds: Pair<String, Any>,
    ): Mono<IArgumentsHandler>

    fun bindAsync(bind: Mono<Pair<String, Any>>): Mono<IArgumentsHandler>

    fun getMap(): Map<String, Any>
}