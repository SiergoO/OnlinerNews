package siergo_o.onlinernews.data.news.repository

import siergo_o.onlinernews.domain.news.repository.NewsRepository
import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.data.rest.model.NetRssFeed
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.model.RssFeed

class NewsRepositoryImpl(private val onlinerApi: OnlinerApi): NewsRepository {
    override fun getOnlinerNews(): RssFeed =
        onlinerApi.feed!!.execute().body()!!.toDomainModel()
}