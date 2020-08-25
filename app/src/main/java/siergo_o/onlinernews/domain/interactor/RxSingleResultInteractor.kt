package siergo_o.onlinernews.domain.interactor

import io.reactivex.Single
import io.reactivex.functions.Cancellable
import io.reactivex.observers.DisposableSingleObserver


abstract class RxSingleResultInteractor<P : Any, R : Any> : SingleResultInteractor<P, R> {

    override fun execute(param: P, callback: SingleResultInteractor.Callback<R>): Cancellable =
        Executor(createSingle(param), callback)

    abstract fun createSingle(param: P): Single<R>

    private class Executor<R : Any>(
        single: Single<R>,
        private val callback: SingleResultInteractor.Callback<R>
    ) : Cancellable {

        private val disposable = single.subscribeWith(object : DisposableSingleObserver<R>() {
            override fun onSuccess(result: R) {
                callback.onResult(result)
            }

            override fun onError(error: Throwable) {
                callback.onFailure(error)
            }
        })

        override fun cancel() {
            disposable.dispose()
        }
    }
}