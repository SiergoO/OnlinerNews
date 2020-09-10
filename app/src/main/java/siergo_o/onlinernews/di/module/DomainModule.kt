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
            @Named("tech") tech: OnlinerApi,
            @Named("people") people: OnlinerApi,
            @Named("auto") auto: OnlinerApi
    ): LoadAllNewsInteractor =
            LoadAllNewsInteractorImpl(NewsRepositoryImpl(tech, people, auto))

    @Provides
    @Singleton
    fun provideLoadNewsFeedInteractor(
            @Named("tech") tech: OnlinerApi,
            @Named("people") people: OnlinerApi,
            @Named("auto") auto: OnlinerApi
    ): LoadNewsFeedInteractor =
            LoadNewsFeedInteractorImpl(NewsRepositoryImpl(tech, people, auto))

    @Provides
    @Singleton
    fun provideSearchNewsInteractor(): SearchNewsInteractor = SearchNewsInteractorImpl()
}