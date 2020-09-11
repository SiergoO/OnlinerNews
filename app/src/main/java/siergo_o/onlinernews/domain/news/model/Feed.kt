package siergo_o.onlinernews.domain.news.model

data class Feed(var feed: MutableMap<Int, RssFeed> = mutableMapOf())