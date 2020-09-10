package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.LoadNewsFeedInteractor
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.interactor.SearchNewsInteractor
import siergo_o.onlinernews.domain.news.model.Feed
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.home.HomeFragmentPresenter
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentPresenter
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    fun provideHomePresenter(loadAllNewsInteractor: LoadAllNewsInteractor, feed: Feed, search: Search):
            HomeFragmentPresenter =
            HomeFragmentPresenter(loadAllNewsInteractor, feed, search)

    @Provides
    fun provideNewsPresenter(loadNewsFeedInteractor: LoadNewsFeedInteractor, searchNewsInteractor: SearchNewsInteractor, feed: Feed, search: Search):
            NewsFragmentPresenter =
            NewsFragmentPresenter(searchNewsInteractor, loadNewsFeedInteractor, feed, search)

    @Provides
    @Singleton
    fun provideNews(): Feed = Feed()

    @Provides
    @Singleton
    fun provideSearch(): Search = Search()
}