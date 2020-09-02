package com.ipictheaters.ipic.presentation.utils.task

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import siergo_o.onlinernews.presentation.utils.RxCache

@Suppress("UNUSED")
class MultiResultTask<P : Any, R : Any, T>(
    private val taskName: String,
    private val creator: (P, T) -> Observable<R>,
    private val valueHandler: ((R, T) -> Unit)?,
    private val errorHandler: ((error: Throwable, T) -> Unit)?,
    private val completeHandler: ((T) -> Unit)? = null
) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun stopPaused(taskName: String, rxCache: RxCache) {
            rxCache.removeObservable<Any>(taskName)?.let { taskDetails ->
                (taskDetails.second as TaskContext<Any>).stopTask.onNext(true)
            }
        }
    }

    private var task: Observable<R>? = null
    private var taskContext: TaskContext<T>? = null
    private var taskSubscription: Disposable? = null
    private val nextConsumer = Consumer<R> { result ->
        val tag = synchronized(this@MultiResultTask) {
            this.taskContext!!.tag
        }
        valueHandler?.invoke(result, tag)
    }
    private val errorConsumer = Consumer<Throwable> { error ->
        val tag = synchronized(this@MultiResultTask) {
            val taskContext = this.taskContext!!
            this.taskContext = null
            this.task = null
            taskContext.tag
        }
        errorHandler?.invoke(error, tag)
    }
    private val completeConsumer = Action {
        val tag = synchronized(this@MultiResultTask) {
            val taskContext = this.taskContext!!
            this.taskContext = null
            this.task = null
            taskContext.tag
        }
        completeHandler?.invoke(tag)
    }

    fun start(param: P, tag: T): Boolean =
        synchronized(this@MultiResultTask) {
            if (taskSubscription.isNullOrDisposed()) {
                val stopTask = BehaviorSubject.create<Boolean>()
                this.task = creator.invoke(param, tag)
                    .takeUntil(stopTask)
                    .replay(1).autoConnect()
                    .takeUntil(stopTask)
                this.taskContext =
                    TaskContext(stopTask, tag)
                this.taskSubscription = this.task!!.subscribe(nextConsumer, errorConsumer, completeConsumer)
                true
            } else false
        }

    fun stop(): Boolean =
        synchronized(this@MultiResultTask) {
            if (!taskSubscription.isNullOrDisposed()) {
                this.taskSubscription?.dispose()
                this.taskContext?.stopTask?.onNext(true)
                this.taskSubscription = null
                this.taskContext = null
                this.task = null
                true
            } else false
        }

    fun pause(rxCache: RxCache): Boolean =
        synchronized(this@MultiResultTask) {
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
        synchronized(this@MultiResultTask) {
            if (taskSubscription.isNullOrDisposed()) {
                val taskDetails = rxCache.removeObservable<R>(taskName)
                if (null != taskDetails) {
                    this.task = taskDetails.first
                    this.taskContext = taskDetails.second as TaskContext<T>
                    this.taskSubscription = this.task!!.subscribe(nextConsumer, errorConsumer, completeConsumer)
                    true
                } else false
            } else false
        }

    fun isRunning(): Boolean =
        synchronized(this@MultiResultTask) {
            !taskSubscription.isNullOrDisposed()
        }

    fun stopPaused(rxCache: RxCache) {
        MultiResultTask.stopPaused(taskName, rxCache)
    }

    private fun Disposable?.isNullOrDisposed(): Boolean =
        (null == this || this.isDisposed)

    private data class TaskContext<T>(
        val stopTask: Subject<Boolean>,
        val tag: T
    )
}
