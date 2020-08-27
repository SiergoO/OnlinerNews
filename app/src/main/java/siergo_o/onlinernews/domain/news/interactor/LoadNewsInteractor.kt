package siergo_o.onlinernews.domain.news.interactor

import siergo_o.onlinernews.domain.interactor.SingleResultInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.home.NewsFragmentContract

interface LoadNewsInteractor :
        SingleResultInteractor<LoadNewsInteractor.Param, LoadNewsInteractor.Result> {

    class Param

    data class Result(
            val feed: List<RssFeed>
    )
}