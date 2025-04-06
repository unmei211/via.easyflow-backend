package via.easyflow.core.tools.database.arguments.resolver

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ByDefaultValueResolver : IArgumentValueResolver {
    override fun resolve(value: Any): Mono<Any> {
        return Mono.just(value)
    }
}