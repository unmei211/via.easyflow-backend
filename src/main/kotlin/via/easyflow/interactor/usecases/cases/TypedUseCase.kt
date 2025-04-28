package via.easyflow.interactor.usecases.cases

import kotlin.reflect.KClass

interface TypedUseCase<T : Any, R> : UseCase<T, R> {
    override fun invoke(input: T): R
    fun getInputType(): KClass<T>
}