package siergo_o.onlinernews.presentation.utils

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.SingleSubject

class SingleResultTask<P : Any, R : Any>(
    private val creator: (P) -> Single<R>,
    private val resultHandler: (R) -> Unit?,
    private val errorHandler: (error: Throwable) -> Unit?
) {

    private var task: Single<R>? = null
    private var taskSubscription: Disposable? = null
    private var taskContext: TaskContext? = null


    private val resultConsumer = Consumer<R> { result ->
        resultHandler.invoke(result)
    }
    private val errorConsumer = Consumer<Throwable> { error ->
        errorHandler.invoke(error)
    }

    fun start(param: P): Boolean =
        synchronized(this@SingleResultTask) {
            if (taskSubscription.isNullOrDisposed()) {
                val stopTask = SingleSubject.create<Boolean>()
                this.task = creator.invoke(param)
                    .takeUntil(stopTask)
                    .cache()
                    .takeUntil(stopTask)
                this.taskSubscription = this.task!!.subscribe(resultConsumer, errorConsumer)
                true
            } else false
        }

    fun stop(): Boolean =
        synchronized(this@SingleResultTask) {
            if (!taskSubscription.isNullOrDisposed()) {
                this.taskSubscription?.dispose()
                this.taskSubscription = null
                this.taskContext = null
                this.task = null
                true
            } else false
        }

    fun isRunning(): Boolean =
        synchronized(this@SingleResultTask) {
            !taskSubscription.isNullOrDisposed()
        }

    private fun Disposable?.isNullOrDisposed(): Boolean =
        (null == this || this.isDisposed)

    private data class TaskContext(
        val stopTask: SingleSubject<Boolean>
    )
}
