package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssChannel

interface HomeFragmentContract {
    interface Ui {
        fun setViewPager(news: List<RssChannel>)
        fun showLoading(show: Boolean)
    }

    interface Presenter {
        fun search(query: String)
    }
}