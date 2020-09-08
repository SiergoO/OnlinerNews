package siergo_o.onlinernews.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias DomainRssItem = siergo_o.onlinernews.domain.news.model.RssItem
typealias UiRssItem = RssItem

@Parcelize
data class RssItem(
        var title: String,
        var link: String,
        var pubDate: String,
        var description: String
) : Parcelable

fun DomainRssItem.toUiModel(): UiRssItem =
        UiRssItem(title, link, pubDate, description)

fun UiRssItem.toDomainModel(): DomainRssItem =
        DomainRssItem(title, link, pubDate, description)

fun List<DomainRssItem>.toUi(): List<UiRssItem> =
        map {
            it.toUiModel()
        }

fun List<UiRssItem>.toDomain(): List<DomainRssItem> =
        map {
            it.toDomainModel()
        }