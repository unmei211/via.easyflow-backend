package via.easyflow.interactor.usecases.manager

import via.easyflow.interactor.usecases.TypedUseCase

interface IUseCaseManager {
    fun <T : Any, R> invoke(input: T): R
    fun <T : Any, R : Any> get(input: T): TypedUseCase<T, R>?
}