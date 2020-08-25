package siergo_o.onlinernews.domain.news.model

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import siergo_o.onlinernews.data.rest.OnlinerApi

class RssService(private val urlLink: String) {
    private val mRetrofit: Retrofit = Retrofit.Builder().baseUrl(urlLink)
            .addConverterFactory(SimpleXmlConverterFactory.create()).build()
    val onlinerApi: OnlinerApi = mRetrofit.create(OnlinerApi::class.java) // TODO Improve retrofit usage

    companion object {
        fun getInstance(urlLink: String): RssService {
            return RssService(urlLink)
        }
    }
}