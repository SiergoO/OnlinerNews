package siergo_o.onlinernews.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias DomainRssNewsChannel = siergo_o.onlinernews.domain.news.model.RssNewsChannel
typealias UiRssNewsChannel = RssNewsChannel

@Parcelize
data class RssNewsChannel(
        var link: String,
        var items: List<RssItem>
): Parcelable

fun DomainRssNewsChannel.toUiModel(): UiRssNewsChannel =
        UiRssNewsChannel(link, items.toUi())

fun UiRssNewsChannel.toDomainModel(): DomainRssNewsChannel =
        DomainRssNewsChannel(link, items.toDomain())



