package via.easyflow.core.tools.database.query.param.filler

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.database.arguments.factory.IArgumentsHandlerFactory
import via.easyflow.core.tools.database.arguments.handler.IArgumentsHandler
import via.easyflow.core.tools.database.query.filter.IQueryFilter
import via.easyflow.core.tools.database.query.param.IQueryParam
import via.easyflow.core.tools.database.query.param.SimpleQueryParam

@Component
class R2dbcQueryParamMapper(
    private val argsFactory: IArgumentsHandlerFactory
) : IQueryParamMapper<DatabaseClient.GenericExecuteSpec, Mono<DatabaseClient.GenericExecuteSpec>> {
    override fun fillByPairs(
        vararg pairs: Pair<String, Any>,
        clientBinder: DatabaseClient.GenericExecuteSpec
    ): Mono<DatabaseClient.GenericExecuteSpec> {
        return this.fill(clientBinder) {
            pairs.map { (key, value) ->
                SimpleQueryParam(value, key)
            }
        }
    }

    private fun fill(
        clientBinder: DatabaseClient.GenericExecuteSpec,
        paramsSupplier: () -> List<IQueryParam>
    ): Mono<DatabaseClient.GenericExecuteSpec> {
        val args = argsFactory.getR2dbc()

        val paramPairs: List<Pair<String, Any>> = paramsSupplier().map { it.getPlaceholder() to it.getValue() }

        val argumentsHandlerMono: Mono<IArgumentsHandler> = args.bind(*paramPairs.toTypedArray())

        return argumentsHandlerMono.map {
            clientBinder.bindValues(it.getMap())
        }
    }

    override fun fillByFilters(
        filters: List<IQueryFilter>,
        clientBinder: DatabaseClient.GenericExecuteSpec
    ): Mono<DatabaseClient.GenericExecuteSpec> {
        return this.fill(
            clientBinder
        ) {
            filters.flatMap {
                it.params()
            }
        }
    }

    override fun fillByParams(
        params: List<IQueryParam>,
        clientBinder: DatabaseClient.GenericExecuteSpec
    ): Mono<DatabaseClient.GenericExecuteSpec> {
        return this.fill(clientBinder) {
            params
        }
    }
}