package siergo_o.onlinernews.presentation.screen.news

import siergo_o.onlinernews.domain.news.model.RssItem

interface NewsFragmentContract {

    interface Ui {
        fun setData(posts: List<RssItem>)
        fun showError(error: String)
        fun showLoading(show: Boolean)
    }

    interface Presenter {
        fun newsRefreshed(tab: TAB)
    }

    enum class TAB {
        TECH,
        PEOPLE,
        AUTO
    }
}