package com.ipictheaters.ipic.presentation.utils.task

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.SingleSubject
import siergo_o.onlinernews.presentation.utils.RxCache


@Suppress("UNUSED")
class SingleResultTask<P : Any, R : Any, T>(
    private val taskName: String,
    private val creator: (P, T) -> Single<R>,
    private val resultHandler: ((R, T) -> Unit)?,
    private val errorHandler: ((error: Throwable, T) -> Unit)?
) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun stopPaused(taskName: String, rxCache: RxCache) {
            rxCache.removeSingle<Any>(taskName)?.let { taskDetails ->
                (taskDetails.second as TaskContext<Any>).stopTask.onSuccess(true)
            }
        }
    }

    private var task: Single<R>? = null
    private var taskContext: TaskContext<T>? = null
    private var taskSubscription: Disposable? = null
    private val resultConsumer = Consumer<R> { result ->
        val tag = synchronized(this@SingleResultTask) {
            val taskContext = this.taskContext!!
            this.taskContext = null
            this.task = null
            taskContext.tag
        }
        resultHandler?.invoke(result, tag)
    }
    private val errorConsumer = Consumer<Throwable> { error ->
        val tag = synchronized(this@SingleResultTask) {
            val taskContext = this.taskContext!!
            this.taskContext = null
            this.task = null
            taskContext.tag
        }
        errorHandler?.invoke(error, tag)
    }

    fun start(param: P, tag: T): Boolean =
        synchronized(this@SingleResultTask) {
            if (taskSubscription.isNullOrDisposed()) {
                val stopTask = SingleSubject.create<Boolean>()
                this.task = creator.invoke(param, tag)
                    .takeUntil(stopTask)
                    .cache()
                    .takeUntil(stopTask)
                this.taskContext =
                    TaskContext(stopTask, tag)
                this.taskSubscription = this.task!!.subscribe(resultConsumer, errorConsumer)
                true
            } else false
        }

    fun stop(): Boolean =
        synchronized(this@SingleResultTask) {
            if (!taskSubscription.isNullOrDisposed()) {
                this.taskSubscription?.dispose()
                this.taskContext?.stopTask?.onSuccess(true)
                this.taskSubscription = null
                this.taskContext = null
                this.task = null
                true
            } else false
        }

    fun pause(rxCache: RxCache): Boolean =
        synchronized(this@SingleResultTask) {
            if (!taskSubscription.isNullOrDisposed()) {
                this.taskSubscription?.dispose()
                rxCache.put(taskName, this.task!!, this.taskContext!!)
                this.taskSubscription = null
                this.taskContext = null
                this.task = null
                true
            } else false
        }

    @Suppress("UNCHECKED_CAST")
    fun resume(rxCache: RxCache): Boolean =
        synchronized(this@SingleResultTask) {
            if (taskSubscription.isNullOrDisposed()) {
                val taskDetails = rxCache.removeSingle<R>(taskName)
                if (null != taskDetails) {
                    this.task = taskDetails.first
                    this.taskContext = taskDetails.second as TaskContext<T>
                    this.taskSubscription = this.task!!.subscribe(resultConsumer, errorConsumer)
                    true
                } else false
            } else false
        }

    fun isRunning(): Boolean =
        synchronized(this@SingleResultTask) {
            !taskSubscription.isNullOrDisposed()
        }

    fun stopPaused(rxCache: RxCache) {
        SingleResultTask.stopPaused(taskName, rxCache)
    }

    private fun Disposable?.isNullOrDisposed(): Boolean =
        (null == this || this.isDisposed)

    private data class TaskContext<T>(
        val stopTask: SingleSubject<Boolean>,
        val tag: T
    )
}
