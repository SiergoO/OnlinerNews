package siergo_o.onlinernews.presentation.utils


interface Cancellable {
    fun cancel()
    fun isCancelled(): Boolean
}