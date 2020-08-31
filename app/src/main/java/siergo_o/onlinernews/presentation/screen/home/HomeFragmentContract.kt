package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.base.BaseMvpContract

interface HomeFragmentContract {
    interface Ui : BaseMvpContract.Ui {
        fun setData(news: List<RssFeed>)
    }

    interface Presenter : BaseMvpContract.Presenter<Ui, Presenter.State> {

        fun newsRefreshed()

        interface State : BaseMvpContract.Presenter.State {

        }
    }
}