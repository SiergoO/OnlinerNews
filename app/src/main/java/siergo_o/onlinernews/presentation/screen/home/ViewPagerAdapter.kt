package siergo_o.onlinernews.presentation.screen.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.news.NewsFragment
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract

class ViewPagerAdapter(fragment: Fragment, private val list: List<RssFeed>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val feedList = list[position]
        return when (position) {
            0 -> NewsFragment.newInstance(NewsFragmentContract.TAB.TECH, feedList)
            1 -> NewsFragment.newInstance(NewsFragmentContract.TAB.PEOPLE, feedList)
            2 -> NewsFragment.newInstance(NewsFragmentContract.TAB.AUTO, feedList)
            else -> NewsFragment.newInstance(NewsFragmentContract.TAB.TECH, feedList)
        }
    }
}