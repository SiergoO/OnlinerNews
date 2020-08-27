package siergo_o.onlinernews.presentation.screen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.news.NewsFragment
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

class ViewPagerAdapter(fragment: Fragment, private val list: List<RssFeed>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NewsFragment(NewsFragmentContract.TAB.TECH, list[position])
        1 -> NewsFragment(NewsFragmentContract.TAB.PEOPLE, list[position])
        2 -> NewsFragment(NewsFragmentContract.TAB.AUTO, list[position])
        else -> NewsFragment(NewsFragmentContract.TAB.TECH, list[position])
    }
}