package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class Search {

    companion object {
        private const val SEARCH_TIMEOUT = 1000L
        private const val MIN_QUERY_SIZE = 3
    }

    private val subject: PublishSubject<String> = PublishSubject.create()

    fun search(query: String) {
        subject.onNext(query)
    }

    fun observe(observer: (String) -> Unit): Disposable =
            subject.debounce(SEARCH_TIMEOUT, TimeUnit.MILLISECONDS)
                    .filter { it.isNotEmpty() && it.length >= MIN_QUERY_SIZE }
                    .map { it.toLowerCase(Locale.ENGLISH).trim() }
                    .distinctUntilChanged().subscribe { observer.invoke(it) }
}