package siergo_o.onlinernews.domain.news.interactor

import siergo_o.onlinernews.domain.interactor.SingleResultInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

interface LoadThematicNewsInteractor : SingleResultInteractor<LoadThematicNewsInteractor.Param, LoadThematicNewsInteractor.Result> {

    data class Param(val tab: NewsFragmentContract.TAB)

    data class Result(val feed: RssFeed)
}