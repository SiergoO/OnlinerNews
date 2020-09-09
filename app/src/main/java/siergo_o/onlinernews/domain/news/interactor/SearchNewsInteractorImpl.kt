package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import siergo_o.onlinernews.domain.interactor.RxMultiResultInteractor
import siergo_o.onlinernews.domain.interactor.asRxObservable
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.utils.Result

class SearchNewsInteractorImpl() : SearchNewsInteractor, RxMultiResultInteractor<SearchNewsInteractor.Param, SearchNewsInteractor.Result>() {

    override fun createObservable(param: SearchNewsInteractor.Param): Observable<SearchNewsInteractor.Result> =
            param.search.asRxObservable()
                    .observeOn(Schedulers.io())
//                    .debounce(searchDebounceTimeout.timeout, searchDebounceTimeout.timeUnit)
                    .switchMap { item ->
                        val result: Result<List<RssItem>> =
                                try {
                                    val news = param.feed.filter { it.title.contains(param.search.get(), true) }
                                    Result.Success(news)
                                } catch (error: Throwable) {
                                    Result.Error(error)
                                }
                        Observable.just(SearchNewsInteractor.Result(item, result))
                    }
}