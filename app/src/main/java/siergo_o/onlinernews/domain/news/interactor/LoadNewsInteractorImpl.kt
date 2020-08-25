package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import siergo_o.onlinernews.domain.interactor.RxSingleResultInteractor
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class LoadNewsInteractorImpl(private val newsRepository: NewsRepository) : LoadNewsInteractor, RxSingleResultInteractor<LoadNewsInteractor.Param, LoadNewsInteractor.Result>() {

    override fun createSingle(param: LoadNewsInteractor.Param): Single<LoadNewsInteractor.Result> =
            Single.defer {
                val news = newsRepository.getOnlinerNews()
                Single.just(LoadNewsInteractor.Result(news))
            }.subscribeOn(Schedulers.io())
}