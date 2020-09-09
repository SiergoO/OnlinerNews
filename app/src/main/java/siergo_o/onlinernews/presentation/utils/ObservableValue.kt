package siergo_o.onlinernews.presentation.utils

import java.util.concurrent.CopyOnWriteArraySet

class ObservableValue<T>(defaultValue: T) {

    private val observers = CopyOnWriteArraySet<ValueObserver<T>>()
    private var value: T = defaultValue

    fun get(): T =
        value

    fun set(value: T) {
        if (this.value != value) {
            this.value = value
            observers.forEach { it.onChanged(value) }
        }
    }

    fun addObserver(observer: ValueObserver<T>, notifyImmediately: Boolean = false) {
        if (observers.add(observer) && notifyImmediately) {
            observer.onChanged(value)
        }
    }

    fun removeObserver(observer: ValueObserver<T>) {
        observers.remove(observer)
    }

    interface ValueObserver<T> {
        fun onChanged(value: T)
    }
}