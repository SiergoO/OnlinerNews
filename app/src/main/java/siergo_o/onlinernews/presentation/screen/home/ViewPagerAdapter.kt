package siergo_o.onlinernews.presentation.screen.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.news.NewsFragment
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NewsFragmentContract.TAB.values().size

    override fun createFragment(position: Int): Fragment =
            NewsFragment.newInstance(NewsFragmentContract.TAB.values()[position])
}