package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import siergo_o.onlinernews.data.rest.OnlinerApi
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
    @Named("tech")
    fun provideTechRetrofit(okHttpClient: OkHttpClient): OnlinerApi =
            createRetrofit(okHttpClient, "tech").create(OnlinerApi::class.java)

    @Provides
    @Singleton
    @Named("people")
    fun providePeopleRetrofit(okHttpClient: OkHttpClient): OnlinerApi =
            createRetrofit(okHttpClient, "people").create(OnlinerApi::class.java)

    @Provides
    @Singleton
    @Named("auto")
    fun provideAutoRetrofit(okHttpClient: OkHttpClient): OnlinerApi =
            createRetrofit(okHttpClient, "auto").create(OnlinerApi::class.java)

    private fun createRetrofit(okHttpClient: OkHttpClient, site: String): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://$site.onliner.by")
                    .addConverterFactory(
                            SimpleXmlConverterFactory.create()
                    )
                    .client(okHttpClient)
                    .build()
}