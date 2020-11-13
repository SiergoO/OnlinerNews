package siergo_o.onlinernews.domain.news.repository

import siergo_o.onlinernews.domain.news.model.RssChannel

interface NewsRepository {
    fun getAllNews(): List<RssChannel>
    fun getTechNews(): RssChannel
    fun getPeopleNews(): RssChannel
    fun getAutoNews(): RssChannel
}