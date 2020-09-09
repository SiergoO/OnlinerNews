package siergo_o.onlinernews.domain.interactor

import siergo_o.onlinernews.presentation.utils.Cancellable


interface MultiResultInteractor<P : Any, R : Any> {

    fun execute(param: P, callback: Callback<R>): Cancellable

    interface Callback<R : Any> {
        fun onResult(result: R)
        fun onFailure(error: Throwable)
        fun onComplete()
    }
}