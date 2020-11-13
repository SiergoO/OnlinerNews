package siergo_o.onlinernews.domain.news.model

data class RssItem(
        var title: String,
        var description: String,
        var link: String,
        var pubDate: Long,
        var imageUrl: String
)