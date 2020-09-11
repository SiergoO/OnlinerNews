package siergo_o.onlinernews.presentation.utils

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

@Suppress("UNUSED")
class MultiResultTask<P : Any, R : Any>(
        private val creator: (P) -> Observable<R>,
        private val valueHandler: ((R) -> Unit)?
) {

    private var task: Observable<R>? = null
    private var taskContext: TaskContext? = null
    private var taskSubscription: Disposable? = null

    private val nextConsumer = Consumer<R> { result -> valueHandler?.invoke(result) }

    fun start(param: P): Boolean =
            synchronized(this@MultiResultTask) {
                if (taskSubscription.isNullOrDisposed()) {
                    val stopTask = BehaviorSubject.create<Boolean>()
                    this.task = creator.invoke(param)
                            .takeUntil(stopTask)
                            .replay(1).autoConnect()
                            .takeUntil(stopTask)
                    this.taskContext = TaskContext(stopTask)
                    this.taskSubscription = this.task!!.subscribe(nextConsumer)
                    true
                } else false
            }

    fun stop(): Boolean =
            synchronized(this@MultiResultTask) {
                if (!taskSubscription.isNullOrDisposed()) {
                    this.taskSubscription?.dispose()
                    this.taskContext!!.stopTask.onNext(true)
                    this.taskSubscription = null
                    this.taskContext = null
                    this.task = null
                    true
                } else false
            }

    fun isRunning(): Boolean =
            synchronized(this@MultiResultTask) {
                !taskSubscription.isNullOrDisposed()
            }

    private fun Disposable?.isNullOrDisposed(): Boolean =
            (null == this || this.isDisposed)

    private data class TaskContext(
            val stopTask: Subject<Boolean>
    )
}
