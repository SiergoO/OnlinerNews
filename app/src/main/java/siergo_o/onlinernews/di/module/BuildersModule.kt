package siergo_o.onlinernews.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import siergo_o.onlinernews.presentation.screen.MainActivity
import siergo_o.onlinernews.presentation.screen.home.HomeFragment
import siergo_o.onlinernews.presentation.screen.news.NewsFragment

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeNewsFragment(): NewsFragment
}