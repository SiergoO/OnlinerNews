package siergo_o.onlinernews.domain.news.model

data class RssNewsChannel(
        var link: String? = null,
        var items: List<RssItem>? = null
)