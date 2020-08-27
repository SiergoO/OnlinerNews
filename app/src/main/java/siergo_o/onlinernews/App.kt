package siergo_o.onlinernews;

import android.app.Application
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.data.rest.OnlinerApiFactory
import siergo_o.onlinernews.domain.news.repository.NewsRepository

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    private val onlinerTechApi: OnlinerApi by lazy {
        OnlinerApiFactory.create(this, "https://tech.onliner.by")
    }
    private val onlinerAutoApi: OnlinerApi by lazy {
        OnlinerApiFactory.create(this, "https://auto.onliner.by")
    }
    private val onlinerPeopleApi: OnlinerApi by lazy {
        OnlinerApiFactory.create(this, "https://people.onliner.by")
    }

    val newsRepository: NewsRepository by lazy { NewsRepositoryImpl(onlinerTechApi, onlinerPeopleApi, onlinerAutoApi) }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}
