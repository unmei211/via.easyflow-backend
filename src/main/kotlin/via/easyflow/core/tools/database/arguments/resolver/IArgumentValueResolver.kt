package via.easyflow.core.tools.database.arguments.resolver

import reactor.core.publisher.Mono

interface IArgumentValueResolver {
    fun resolve(value: Any): Mono<Any>
}