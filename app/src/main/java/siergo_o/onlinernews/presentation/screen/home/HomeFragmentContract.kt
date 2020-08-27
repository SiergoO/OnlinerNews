package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.base.BaseMvpContract

interface HomeFragmentContract {
    interface Ui : BaseMvpContract.Ui {
        fun setData(news: List<RssFeed>)
        fun showLoading(isShown: Boolean)

    }

    interface Presenter : BaseMvpContract.Presenter<Ui, Presenter.State> {

        interface State : BaseMvpContract.Presenter.State {

        }
    }
}