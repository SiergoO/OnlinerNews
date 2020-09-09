package siergo_o.onlinernews.domain.news.interactor

import siergo_o.onlinernews.domain.interactor.MultiResultInteractor
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.utils.ObservableValue

interface SearchNewsInteractor : MultiResultInteractor<SearchNewsInteractor.Param, SearchNewsInteractor.Result> {

    data class Param(val search: ObservableValue<String>, val feed: List<RssItem>)
    data class Result(val search: String,
                      val result: siergo_o.onlinernews.presentation.utils.Result<List<RssItem>>)
}