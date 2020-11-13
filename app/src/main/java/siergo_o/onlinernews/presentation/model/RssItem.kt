package siergo_o.onlinernews.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias DomainRssItem = siergo_o.onlinernews.domain.news.model.RssItem
typealias UiRssItem = RssItem

@Parcelize
data class RssItem(
        var title: String,
        var description: String,
        var link: String,
        var pubDate: Long,
        var imageUrl: String
) : Parcelable

fun DomainRssItem.toUiModel(): UiRssItem =
        UiRssItem(title, description, link, pubDate, imageUrl)

fun toDomainModel(rssItem: DomainRssItem): DomainRssItem =
        rssItem

fun List<DomainRssItem>.toUi(): List<UiRssItem> =
        map {
            it.toUiModel()
        }

fun List<UiRssItem>.toDomain(): List<DomainRssItem> =
        map {
            toDomainModel(DomainRssItem(it.title, it.description, it.link, it.pubDate, it.imageUrl))
        }