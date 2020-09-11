package siergo_o.onlinernews.di.module

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import dagger.Module
import dagger.Provides
import siergo_o.onlinernews.presentation.screen.home.HomeFragment
import siergo_o.onlinernews.presentation.screen.home.ViewPagerAdapter
import siergo_o.onlinernews.presentation.screen.news.NewsAdapter
import javax.inject.Singleton

@Module
class AdapterModule {
    @Provides
    @Singleton
    fun provideNewsAdapter(): NewsAdapter = NewsAdapter()

    @Provides
    @Singleton
    fun provideViewPagerAdapter(fragment: Fragment) = ViewPagerAdapter(fragment as HomeFragment)
}