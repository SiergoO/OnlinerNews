package siergo_o.onlinernews

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4

object Singles {

    inline fun <T1, T2, R> zip(source1: Single<T1>, source2: Single<T2>, crossinline combineFunction: (T1, T2) -> R) =
            Single.zip(source1, source2,
                    BiFunction<T1, T2, R> { t1, t2 -> combineFunction(t1, t2) })!!

    inline fun <T1, T2, T3, R> zip(
            source1: Single<T1>,
            source2: Single<T2>,
            source3: Single<T3>,
            crossinline combineFunction: (T1, T2, T3) -> R
    ): Single<R> =
            Single.zip(source1, source2, source3,
                    Function3<T1, T2, T3, R> { t1, t2, t3 -> combineFunction(t1, t2, t3) })

    inline fun <T1, T2, T3, T4, R> zip(
            source1: Single<T1>,
            source2: Single<T2>,
            source3: Single<T3>,
            source4: Single<T4>,
            crossinline combineFunction: (T1, T2, T3, T4) -> R
    ): Single<R> =
            Single.zip(source1, source2, source3, source4,
                    Function4<T1, T2, T3, T4, R> { t1, t2, t3, t4 -> combineFunction(t1, t2, t3, t4) })
}