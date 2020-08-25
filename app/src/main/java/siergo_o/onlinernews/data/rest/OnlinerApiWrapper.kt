package siergo_o.onlinernews.data.rest

import retrofit2.Call
import siergo_o.onlinernews.data.rest.model.NetRssFeed

internal class OnlinerApiWrapper(private val onlinerApi: OnlinerApi): OnlinerApi {
    override val feed: Call<NetRssFeed?>? = onlinerApi.feed
}