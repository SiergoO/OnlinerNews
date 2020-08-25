package siergo_o.onlinernews;

import android.app.Application
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.data.rest.OnlinerApiFactory
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    val onlinerApi: OnlinerApi by lazy { OnlinerApiFactory.create(this, "https://tech.onliner.by/") }
    val newsRepository: NewsRepository by lazy { NewsRepositoryImpl(onlinerApi) }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}
