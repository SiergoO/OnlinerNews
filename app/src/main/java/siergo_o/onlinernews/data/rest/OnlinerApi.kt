package siergo_o.onlinernews.data.rest

import retrofit2.Call
import retrofit2.http.GET
import siergo_o.onlinernews.data.rest.model.NetRssFeed

interface OnlinerApi {
    @GET("/feed")
    fun getFeed(): Call<NetRssFeed?>?
}
