package via.easyflow.interactor.usecases.invoker

import org.springframework.stereotype.Component
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import kotlin.reflect.KClass

@Component
class UserCaseInvoker(
    @Case(scope = CaseScope.USER) private val usecasesMap: Map<KClass<*>, TypedUseCase<*, *>>
) : CaseInvoker {
    private val useCasesByInputType = usecasesMap
    override fun <T : TypedUseCase<F, S>, F : Any, S : Any> invoke(input: F): S {
        val inputType = input::class

        val useCase = useCasesByInputType[inputType] as? TypedUseCase<F, S>
            ?: throw IllegalArgumentException("No use case found for input type: $inputType")
        return useCase.invoke(input)
    }

    override fun <F : Any, S : Any> get(input: F): TypedUseCase<F, S>? {
        val useCase = useCasesByInputType[input::class] as? TypedUseCase<F, S>
        return useCase
    }
}