package via.easyflow.interactor.usecases.matcher

import org.springframework.stereotype.Component
import via.easyflow.interactor.usecases.TypedUseCase
import kotlin.reflect.KClass

@Component
class UseCasesInputMatcher : IUseCasesInputMatcher {
    override fun match(useCases: List<TypedUseCase<*, *>>): Map<KClass<*>, TypedUseCase<*, *>> {
        val map = useCases.toSet().associateBy {
            it.getInputType()
        }
        return map
    }
}