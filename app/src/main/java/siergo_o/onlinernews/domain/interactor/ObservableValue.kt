package siergo_o.onlinernews.domain.interactor

import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import siergo_o.onlinernews.presentation.utils.ObservableValue

fun <T> ObservableValue<T>.asRxObservable(): Observable<T> =
    Observable.create { emitter ->
        val valueObserver = object : ObservableValue.ValueObserver<T> {
            override fun onChanged(value: T) {
                if (!emitter.isDisposed) {
                    emitter.onNext(value)
                }
            }
        }
        if (!emitter.isDisposed) {
            emitter.setDisposable(Disposables.fromAction { this@asRxObservable.removeObserver(valueObserver) })
            this@asRxObservable.addObserver(valueObserver, true)
        }
    }