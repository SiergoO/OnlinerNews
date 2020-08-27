package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.base.BaseMvpContract

interface NewsFragmentContract : BaseMvpContract {

    interface Ui : BaseMvpContract.Ui {
        fun setData(posts: List<RssItem>)
        fun showToast()
        fun showLoading(isShown: Boolean)
    }

    interface Presenter : BaseMvpContract.Presenter<Ui, Presenter.State> {

        fun newsRefreshed()

        interface State : BaseMvpContract.Presenter.State {

        }
    }

    enum class TAB {
        TECH,
        PEOPLE,
        AUTO
    }
}