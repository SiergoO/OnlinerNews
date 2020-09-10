package siergo_o.onlinernews.presentation.screen.home

import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.model.Feed
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.news.NewsFragmentContract
import siergo_o.onlinernews.presentation.utils.asRxSingle
import java.lang.Exception

class HomeFragmentPresenter(
        private val loadAllNewsInteractor: LoadAllNewsInteractor,
        private val feed: Feed,
        private val search: Search
) : HomeFragmentContract.Presenter {

    companion object {
        private const val FLAG_SETUP_HOME_UI = 0x0001
        private const val TASK_LOAD_NEWS = "loadNews"
    }

    private val taskLoadNews = loadNewsTask()
    private lateinit var ui: HomeFragment

    override fun start(ui: HomeFragment) {
        this.ui = ui
        if (feed.feed.isEmpty()) {
            taskLoadNews.start(LoadAllNewsInteractor.Param(), Unit)
        }
    }

    override fun search(query: String) {
        search.search(query)
    }

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_HOME_UI)) {
            if (feed.feed.isNotEmpty()) {
                ui.setViewPager(feed.feed.values.asSequence().toList())
            }
        }
        ui.showLoading(taskLoadNews.isRunning())
    }

    private fun handleLoadNews(
            data: LoadAllNewsInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feed.feed[0] = data.feed[0]
            feed.feed[1] = data.feed[1]
            feed.feed[2] = data.feed[2]
        } else if (error != null) {
            throw Exception()
        }
        updateUi(FLAG_SETUP_HOME_UI)
    }

    private fun loadNewsTask() =
            SingleResultTask<LoadAllNewsInteractor.Param, LoadAllNewsInteractor.Result, Unit>(
                    TASK_LOAD_NEWS,
                    { param: LoadAllNewsInteractor.Param, _: Unit ->
                        loadAllNewsInteractor.asRxSingle(param)
                                .observeOn(AndroidSchedulers.mainThread())
                    },
                    { result: LoadAllNewsInteractor.Result, _: Unit ->
                        handleLoadNews(result, null)
                    },
                    { error: Throwable, _: Unit ->
                        handleLoadNews(null, error)
                    }
            )
}