package siergo_o.onlinernews.domain.interactor

import io.reactivex.functions.Cancellable


interface SingleResultInteractor<P : Any, R : Any> {

    fun execute(param: P, callback: Callback<R>): Cancellable

    interface Callback<R: Any> {
        fun onResult(result: R)
        fun onFailure(error: Throwable)
    }
}