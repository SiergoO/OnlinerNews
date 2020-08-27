package siergo_o.onlinernews.domain.news.repository

import siergo_o.onlinernews.domain.news.model.RssFeed

interface NewsRepository {
    fun getNews(): List<RssFeed>
}