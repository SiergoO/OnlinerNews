package siergo_o.onlinernews.presentation.utils

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.ConcurrentHashMap


@Suppress("unused")
class RxCache {

    private val observableCache = ConcurrentHashMap<String, Pair<Observable<*>, Any?>>()
    private val flowableCache = ConcurrentHashMap<String, Pair<Flowable<*>, Any?>>()
    private val singleCache = ConcurrentHashMap<String, Pair<Single<*>, Any?>>()

    @SuppressLint("CheckResult")
    fun <T> put(key: String, observable: Observable<T>, tag: Any?) {
        observableCache[key] = Pair(observable, tag)
    }

    @SuppressLint("CheckResult")
    fun <T> put(key: String, flowable: Flowable<T>, tag: Any?) {
        flowableCache[key] = Pair(flowable, tag)
    }

    @SuppressLint("CheckResult")
    fun <T> put(key: String, single: Single<T>, tag: Any?) {
        singleCache[key] = Pair(single, tag)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> removeObservable(key: String): Pair<Observable<T>, Any?>? =
        observableCache.remove(key) as? Pair<Observable<T>, Any?>

    @Suppress("UNCHECKED_CAST")
    fun <T> removeFlowable(key: String): Pair<Flowable<T>, Any?>? =
        flowableCache.remove(key) as? Pair<Flowable<T>, Any?>

    @Suppress("UNCHECKED_CAST")
    fun <T> removeSingle(key: String): Pair<Single<T>, Any?>? =
        singleCache.remove(key) as? Pair<Single<T>, Any?>?

    fun hasObservable(key: String): Boolean =
        observableCache.containsKey(key)

    fun hasFlowable(key: String): Boolean =
        flowableCache.containsKey(key)

    fun hasSingle(key: String): Boolean =
        singleCache.containsKey(key)
}