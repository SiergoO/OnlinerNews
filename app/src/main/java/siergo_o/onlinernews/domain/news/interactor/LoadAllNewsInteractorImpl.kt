package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import siergo_o.onlinernews.domain.interactor.RxSingleResultInteractor
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class LoadAllNewsInteractorImpl(private val newsRepository: NewsRepository) : LoadAllNewsInteractor, RxSingleResultInteractor<LoadAllNewsInteractor.Param, LoadAllNewsInteractor.Result>() {

    override fun createSingle(param: LoadAllNewsInteractor.Param): Single<LoadAllNewsInteractor.Result> =

            Single.defer {
                val news = newsRepository.getAllNews()
                Single.just(LoadAllNewsInteractor.Result(news))
            }.subscribeOn(Schedulers.io())
}