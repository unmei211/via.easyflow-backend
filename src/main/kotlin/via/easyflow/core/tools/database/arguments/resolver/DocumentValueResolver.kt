package via.easyflow.core.tools.database.arguments.resolver

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.core.tools.json.io.ReactiveJsonIO

@Component
class DocumentValueResolver(
    private val mapper: ReactiveJsonIO
) : IArgumentValueResolver {
    override fun resolve(value: Any): Mono<Any> {
        return mapper.toJson(value).map { it as Any }
    }
}