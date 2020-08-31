package siergo_o.onlinernews.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias DomainRssFeed = siergo_o.onlinernews.domain.news.model.RssFeed
typealias UiRssFeed = RssFeed

@Parcelize
data class RssFeed(val channel: RssNewsChannel): Parcelable

fun DomainRssFeed.toUiModel(): UiRssFeed =
        UiRssFeed(channel.toUiModel())

fun UiRssFeed.toDomainModel(): DomainRssFeed =
        DomainRssFeed(channel.toDomainModel())