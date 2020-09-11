package siergo_o.onlinernews.domain.news.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import siergo_o.onlinernews.domain.interactor.RxSingleResultInteractor
import siergo_o.onlinernews.domain.news.repository.NewsRepository
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

class LoadThematicNewsInteractorImpl(private val newsRepository: NewsRepository) : LoadThematicNewsInteractor, RxSingleResultInteractor<LoadThematicNewsInteractor.Param, LoadThematicNewsInteractor.Result>() {
    override fun createSingle(param: LoadThematicNewsInteractor.Param): Single<LoadThematicNewsInteractor.Result> =
            Single.defer {
                val news = when (param.tab) {
                    NewsFragmentContract.TAB.TECH -> newsRepository.getTechNews()
                    NewsFragmentContract.TAB.PEOPLE -> newsRepository.getPeopleNews()
                    NewsFragmentContract.TAB.AUTO -> newsRepository.getAutoNews()
                }
                Single.just(LoadThematicNewsInteractor.Result(news))
            }.subscribeOn(Schedulers.io())
}