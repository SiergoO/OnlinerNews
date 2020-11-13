package siergo_o.onlinernews.data.rest.model

import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.domain.news.model.RssChannel
import siergo_o.onlinernews.presentation.utils.getImageUrl

fun NetRssChannel.toDomainModel(): RssChannel = RssChannel(
        this.items.map { it.toDomainModel() }
)

fun NetRssItem.toDomainModel(): RssItem = RssItem(
        this.title,
        this.description,
        this.link,
        this.pubDate.toLong(),
        this.description.getImageUrl()
)