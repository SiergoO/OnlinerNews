package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.base.BaseMvpContract

interface HomeFragmentContract {
    interface Ui {
        fun setViewPager(news: List<RssFeed>)
        fun showLoading(show: Boolean)
    }

    interface Presenter {

        fun start(ui: HomeFragment)
        fun search(query: String)

        interface State {

        }
    }
}