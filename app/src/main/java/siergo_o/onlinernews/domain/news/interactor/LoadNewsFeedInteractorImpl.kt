package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import siergo_o.onlinernews.domain.interactor.RxSingleResultInteractor
import siergo_o.onlinernews.domain.news.repository.NewsRepository
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

class LoadNewsFeedInteractorImpl(private val newsRepository: NewsRepository) : LoadNewsFeedInteractor, RxSingleResultInteractor<LoadNewsFeedInteractor.Param, LoadNewsFeedInteractor.Result>() {
    override fun createSingle(param: LoadNewsFeedInteractor.Param): Single<LoadNewsFeedInteractor.Result> =
            Single.defer {
                val news = when (param.tab) {
                    NewsFragmentContract.TAB.TECH -> newsRepository.getTechNews()
                    NewsFragmentContract.TAB.PEOPLE -> newsRepository.getPeopleNews()
                    NewsFragmentContract.TAB.AUTO -> newsRepository.getAutoNews()
                }
                Single.just(LoadNewsFeedInteractor.Result(news))
            }.subscribeOn(Schedulers.io())
}