package siergo_o.onlinernews.model

import retrofit2.Call
import retrofit2.http.GET

interface OnlinerApi {
    @get:GET("/feed")
    val feed: Call<RssFeed?>?
}
