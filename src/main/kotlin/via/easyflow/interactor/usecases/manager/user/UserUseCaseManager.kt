package via.easyflow.interactor.usecases.manager.user

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableCase
import via.easyflow.interactor.usecases.cases.user.UserIsAvailableUseCaseInput
import via.easyflow.interactor.usecases.cases.user.UsersMustBeAvailableCase
import via.easyflow.interactor.usecases.cases.user.UsersMustBeAvailableCaseInput
import via.easyflow.interactor.usecases.manager.IUseCaseManager
import kotlin.reflect.KClass

@Component
class UseCaseManager(
    private val userIsAvailableCase: UserIsAvailableCase,
) : IUseCaseManager {
    private val useCasesByInputType = mutableMapOf<KClass<*>, UseCase<*, *>>()

    init {
        useCasesByInputType[UserIsAvailableUseCaseInput::class] = userIsAvailableCase
    }

    override fun <T, R> invoke(input: T): R {
        val inputType = input!!::class
        val useCase = useCasesByInputType[inputType] as? UseCase<T, R>
            ?: throw IllegalArgumentException("No use case found for input type: $inputType")
        return useCase.invoke(input)
    }
}