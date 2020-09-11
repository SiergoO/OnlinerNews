package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.LoadThematicNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.interactor.SearchNewsInteractor
import siergo_o.onlinernews.domain.news.model.Feed
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
    fun provideNewsPresenter(loadThematicNewsInteractor: LoadThematicNewsInteractor, searchNewsInteractor: SearchNewsInteractor, feed: Feed, search: Search):
            NewsFragmentPresenter =
            NewsFragmentPresenter(searchNewsInteractor, loadThematicNewsInteractor, feed, search)

    @Provides
    @Singleton
    fun provideNews(): Feed = Feed()

    @Provides
    @Singleton
    fun provideSearch(): Search = Search()
}