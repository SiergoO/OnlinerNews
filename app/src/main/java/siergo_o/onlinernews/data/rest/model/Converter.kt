package siergo_o.onlinernews.data.rest.model

import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.domain.news.model.RssNewsChannel

fun NetRssFeed.toDomainModel(): RssFeed = RssFeed(
        this.channel.toDomainModel()
)

fun NetRssNewsChannel.toDomainModel(): RssNewsChannel = RssNewsChannel(
        this.link,
        this.items.map { it.toDomainModel() }
)

fun NetRssItem.toDomainModel(): RssItem = RssItem(
        this.getTitle(),
        this.link,
        this.getPubDate(),
        this.category,
        this.description
)