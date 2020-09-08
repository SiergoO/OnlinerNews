package siergo_o.onlinernews.domain.news.model

data class RssItem(
        var title: String,
        var link: String,
        var pubDate: String,
        var description: String
)