package siergo_o.onlinernews.domain.news.repository

import siergo_o.onlinernews.domain.news.model.RssFeed

interface NewsRepository {
    fun getAllNews(): List<RssFeed>
    fun getTechNews(): RssFeed
    fun getPeopleNews(): RssFeed
    fun getAutoNews(): RssFeed
}