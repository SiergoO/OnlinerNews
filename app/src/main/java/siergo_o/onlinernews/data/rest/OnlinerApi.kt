package siergo_o.onlinernews.data.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import siergo_o.onlinernews.data.rest.model.NetRssChannel

interface OnlinerApi {
    @GET("convert/")
    fun getFeed(@Query("u") url: String): Call<NetRssChannel?>?
}
