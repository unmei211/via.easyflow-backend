package via.easyflow.interactor.usecases.invoker

import via.easyflow.interactor.usecases.cases.TypedUseCase

interface CaseInvoker {
    fun <T : TypedUseCase<F, S>, F : Any, S : Any> invoke(input: F): S
    fun <F : Any, S : Any> get(input: F): TypedUseCase<F, S>?
}