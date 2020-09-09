package siergo_o.onlinernews.presentation.utils

import io.reactivex.Observable
import io.reactivex.Single
import siergo_o.onlinernews.domain.interactor.MultiResultInteractor
import siergo_o.onlinernews.domain.interactor.SingleResultInteractor

fun <P : Any, R : Any> SingleResultInteractor<P, R>.asRxSingle(param: P): Single<R> =
        Single.defer { createSingle(param) }

fun <P : Any, R : Any> MultiResultInteractor<P, R>.asRxObservable(param: P): Observable<R> =
        Observable.defer { createsObservable(param) }

private fun <P : Any, R : Any> SingleResultInteractor<P, R>.createSingle(param: P): Single<R> =
        Single.create { emitter ->
            val interactorCallback = object : SingleResultInteractor.Callback<R> {
                override fun onResult(result: R) {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(result)
                    }
                }

                override fun onFailure(error: Throwable) {
                    if (!emitter.isDisposed) {
                        emitter.onError(error)
                    }
                }
            }
            val cancellable = this@createSingle.execute(param, interactorCallback)
            emitter.setCancellable { cancellable.cancel() }
        }

private fun <P : Any, R : Any> MultiResultInteractor<P, R>.createsObservable(param: P): Observable<R> =
        Observable.create { emitter ->
            val interactorCallback = object : MultiResultInteractor.Callback<R> {
                override fun onResult(result: R) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(result)
                    }
                }

                override fun onFailure(error: Throwable) {
                    if (!emitter.isDisposed) {
                        emitter.onError(error)
                    }
                }

                override fun onComplete() {
                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
            }
            val cancellable = this@createsObservable.execute(param, interactorCallback)
            emitter.setCancellable { cancellable.cancel() }
        }