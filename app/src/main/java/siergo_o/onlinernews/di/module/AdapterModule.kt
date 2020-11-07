package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.Provides
import siergo_o.onlinernews.presentation.screen.news.NewsAdapter

@Module
class AdapterModule {
    @Provides
    fun provideNewsAdapter(): NewsAdapter = NewsAdapter()
}