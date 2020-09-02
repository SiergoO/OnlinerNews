package siergo_o.onlinernews.domain.news.model

data class RssNewsChannel(
        var link: String,
        var items: List<RssItem>
)