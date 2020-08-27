package siergo_o.onlinernews.data.news.repository

import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class NewsRepositoryImpl(private val onlinerTechApi: OnlinerApi, private val onlinerPeopleApi: OnlinerApi, private val onlinerAutoApi: OnlinerApi) : NewsRepository {

    override fun getNews(): List<RssFeed>  {
        val tech = onlinerTechApi.getFeed()!!.execute().body()!!.toDomainModel()
        val people = onlinerPeopleApi.getFeed()!!.execute().body()!!.toDomainModel()
        val auto = onlinerAutoApi.getFeed()!!.execute().body()!!.toDomainModel()
        return mutableListOf(tech, people, auto)
    }
}