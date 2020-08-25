package siergo_o.onlinernews.data.rest

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object OnlinerApiFactory {

    private const val HTTP_CONNECT_TIMEOUT_MS = 20 * 1000
    private const val HTTP_READ_TIMEOUT_MS = 20 * 1000

    fun create(context: Context, baseUrl: String): OnlinerApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(HTTP_CONNECT_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
            readTimeout(HTTP_READ_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                        SimpleXmlConverterFactory.create()
                )
                .client(okHttpClient)
                .build()
        return OnlinerApiWrapper(retrofit.create<OnlinerApi>(OnlinerApi::class.java))
    }
}