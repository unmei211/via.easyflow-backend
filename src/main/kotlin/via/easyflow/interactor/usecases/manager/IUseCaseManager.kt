package via.easyflow.interactor.usecases.manager

import reactor.core.publisher.Mono
import via.easyflow.interactor.usecases.UseCase
import kotlin.reflect.KClass

interface IUseCaseManager {
    fun <T, R> invoke(input: T): R
}