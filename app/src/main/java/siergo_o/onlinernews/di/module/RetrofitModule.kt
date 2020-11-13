package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import siergo_o.onlinernews.data.rest.OnlinerApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    companion object {
        private const val HTTP_CONNECT_TIMEOUT_MS = 20 * 1000
        private const val HTTP_READ_TIMEOUT_MS = 20 * 1000
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(HTTP_CONNECT_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
        readTimeout(HTTP_READ_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
        addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }.build()

    @Provides
    @Singleton
    fun provideTechRetrofit(okHttpClient: OkHttpClient): OnlinerApi =
            createRetrofit(okHttpClient).create(OnlinerApi::class.java)

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://send-rss-get-json.herokuapp.com/")
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .client(okHttpClient)
                    .build()
}