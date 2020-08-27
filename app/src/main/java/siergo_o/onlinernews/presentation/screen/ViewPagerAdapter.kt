package siergo_o.onlinernews.presentation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import siergo_o.onlinernews.presentation.screen.home.NewsFragment
import siergo_o.onlinernews.presentation.screen.home.NewsFragmentContract

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NewsFragment(NewsFragmentContract.TAB.TECH)
        1 -> NewsFragment(NewsFragmentContract.TAB.PEOPLE)
        2 -> NewsFragment(NewsFragmentContract.TAB.AUTO)
        else -> NewsFragment(NewsFragmentContract.TAB.TECH)
    }
}