package via.easyflow.core.tools.database.arguments.factory

import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.handler.R2dbcArgumentsHandler
import via.easyflow.core.tools.database.arguments.handler.SimpleArgumentsHandler

interface IArgumentsHandlerFactory {
    fun getSimple(): SimpleArgumentsHandler

    fun getR2dbc(): R2dbcArgumentsHandler

    fun getSimpleAsync(): Mono<SimpleArgumentsHandler>

    fun getR2dbcAsync(): Mono<R2dbcArgumentsHandler>
}