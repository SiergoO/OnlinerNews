package siergo_o.onlinernews.presentation.screen.news

import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.base.BaseMvpContract

interface NewsFragmentContract : BaseMvpContract {

    interface Ui {
        fun setData(posts: List<RssItem>)
        fun showToast()
        fun showLoading(show: Boolean)
        fun setStates(posts: List<RssItem>)

    }

    interface Presenter {

        fun start(ui: NewsFragment)
        fun newsRefreshed(tab: TAB)

        interface State {

        }
    }

    enum class TAB {
        TECH,
        PEOPLE,
        AUTO
    }
}