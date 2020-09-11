package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssFeed

interface HomeFragmentContract {
    interface Ui {
        fun setViewPager(news: List<RssFeed>)
        fun showLoading(show: Boolean)
    }

    interface Presenter {
        fun search(query: String)
    }
}