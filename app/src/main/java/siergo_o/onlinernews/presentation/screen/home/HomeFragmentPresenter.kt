package siergo_o.onlinernews.presentation.screen.home

import siergo_o.onlinernews.presentation.utils.SingleResultTask
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.model.Feed
import siergo_o.onlinernews.presentation.screen.BasePresenter
import siergo_o.onlinernews.presentation.utils.asRxSingle

class HomeFragmentPresenter(
    private val loadAllNewsInteractor: LoadAllNewsInteractor,
    private val feed: Feed,
    private val search: Search
) : BasePresenter, HomeFragmentContract.Presenter {

    private lateinit var ui: HomeFragment
    private val taskLoadAllNews = createLoadAllNewsTask()

    override fun start(ui: DaggerFragment) {
        this.ui = ui as HomeFragment
        if (feed.feed.isEmpty()) {
            taskLoadAllNews.start(LoadAllNewsInteractor.Param())
        }
        updateUi()
    }

    override fun search(query: String) {
        search.search(query)
    }

    private fun updateUi() {
        if (feed.feed.isNotEmpty()) {
            ui.setViewPager(feed.feed.values.toList())
        }
        ui.showLoading(taskLoadAllNews.isRunning())
    }

    private fun handleLoadNewsData(data: LoadAllNewsInteractor.Result) {
        for (i in data.feed.indices) feed.feed[i] = data.feed[i]
        updateUi()
    }

    private fun handleLoadNewsError(error: Throwable) {
        ui.showError(error.message.toString())
        updateUi()
    }

    private fun createLoadAllNewsTask() =
        SingleResultTask<LoadAllNewsInteractor.Param, LoadAllNewsInteractor.Result>(
            { param: LoadAllNewsInteractor.Param ->
                loadAllNewsInteractor.asRxSingle(param)
                    .observeOn(AndroidSchedulers.mainThread())
            },
            { result: LoadAllNewsInteractor.Result ->
                handleLoadNewsData(result)
            },
            { error: Throwable ->
                handleLoadNewsError(error)
            }
        )
}