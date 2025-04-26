package via.easyflow.interactor.usecases.manager.user

import org.springframework.stereotype.Component
import via.easyflow.interactor.usecases.TypedUseCase
import via.easyflow.interactor.usecases.annotation.Case
import via.easyflow.interactor.usecases.annotation.CaseScope
import via.easyflow.interactor.usecases.manager.IUseCaseManager
import kotlin.reflect.KClass

@Component
class UserUseCaseManager(
    @Case(scope = CaseScope.USER) private val usecasesMap: Map<KClass<*>, TypedUseCase<*, *>>
) : IUseCaseManager {
    private val useCasesByInputType = usecasesMap

    override fun <T : Any, R> invoke(input: T): R {
        val inputType = input::class
        val useCase = useCasesByInputType[inputType] as? TypedUseCase<T, R>
            ?: throw IllegalArgumentException("No use case found for input type: $inputType")
        return useCase.invoke(input)
    }

    override fun <T : Any, R : Any> get(input: T): TypedUseCase<T, R>? {
        val useCase = useCasesByInputType[input::class] as? TypedUseCase<T, R>
        return useCase
    }
}