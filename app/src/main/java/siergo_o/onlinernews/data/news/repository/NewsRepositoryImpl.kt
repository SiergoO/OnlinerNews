package siergo_o.onlinernews.data.news.repository

import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.model.RssChannel
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class NewsRepositoryImpl(private val onlinerApi: OnlinerApi) : NewsRepository {

    override fun getAllNews(): List<RssChannel>  {
        val tech = onlinerApi.getFeed("https://tech.onliner.by/feed")!!.execute().body()!!.toDomainModel()
        val people = onlinerApi.getFeed("https://people.onliner.by/feed")!!.execute().body()!!.toDomainModel()
        val auto = onlinerApi.getFeed("https://auto.onliner.by/feed")!!.execute().body()!!.toDomainModel()
        return mutableListOf(tech, people, auto)
    }

    override fun getTechNews(): RssChannel =
        onlinerApi.getFeed("https://tech.onliner.by/feed")!!.execute().body()!!.toDomainModel()

    override fun getPeopleNews(): RssChannel =
        onlinerApi.getFeed("https://people.onliner.by/feed")!!.execute().body()!!.toDomainModel()

    override fun getAutoNews(): RssChannel =
        onlinerApi.getFeed("https://auto.onliner.by/feed")!!.execute().body()!!.toDomainModel()
}