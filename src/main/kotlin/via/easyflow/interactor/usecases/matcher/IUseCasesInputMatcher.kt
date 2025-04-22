package via.easyflow.interactor.usecases.matcher

import via.easyflow.interactor.usecases.TypedUseCase
import kotlin.reflect.KClass

interface IUseCasesInputMatcher {
    fun match(useCases: List<TypedUseCase<*, *>>): Map<KClass<*>, TypedUseCase<*, *>>
}