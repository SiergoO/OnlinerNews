package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class Search {

    companion object {
        private const val SEARCH_TIMEOUT = 200L
    }

    private val subject: PublishSubject<String> = PublishSubject.create()

    fun search(query: String) {
        subject.onNext(query)
    }

    fun observe(observer: (String) -> Unit): Disposable =
            subject.debounce(SEARCH_TIMEOUT, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .subscribe { observer.invoke(it) }
}