package siergo_o.onlinernews.domain.news.interactor

import siergo_o.onlinernews.domain.interactor.SingleResultInteractor
import siergo_o.onlinernews.domain.news.model.RssChannel

interface LoadAllNewsInteractor :
        SingleResultInteractor<LoadAllNewsInteractor.Param, LoadAllNewsInteractor.Result> {

    class Param

    data class Result(
            val feed: List<RssChannel>
    )
}