package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.domain.news.interactor.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideLoadAllNewsInteractor(
            onlinerApi: OnlinerApi
    ): LoadAllNewsInteractor =
            LoadAllNewsInteractorImpl(NewsRepositoryImpl(onlinerApi))

    @Provides
    @Singleton
    fun provideLoadNewsFeedInteractor(
            onlinerApi: OnlinerApi
    ): LoadThematicNewsInteractor =
            LoadThematicNewsInteractorImpl(NewsRepositoryImpl(onlinerApi))

    @Provides
    @Singleton
    fun provideSearchNewsInteractor(): SearchNewsInteractor = SearchNewsInteractorImpl()
}