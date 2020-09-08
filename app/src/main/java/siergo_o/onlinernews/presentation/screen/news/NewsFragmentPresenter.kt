package siergo_o.onlinernews.presentation.screen.news;

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadNewsFeedInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(private val tab: NewsFragmentContract.TAB,
                            private val feed: RssFeed,
                            private val loadNewsFeedInteractor: LoadNewsFeedInteractor) :
        BaseMvpPresenter<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State>(), NewsFragmentContract.Presenter {

    companion object {
        private const val FLAG_SETUP_UI = 0x0001
        private const val TASK_LOAD_NEWS_FEED = "sendDiningCheckReceipt"
    }

    override fun start() {
        updateUi(FLAG_SETUP_UI)
    }

    private var feedList: RssFeed? = null
    private val taskLoadNewsFeed = loadNewsFeedTask()

    override fun newsRefreshed(tab: NewsFragmentContract.TAB) {
        taskLoadNewsFeed.start(LoadNewsFeedInteractor.Param(tab), Unit)
    }

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_UI)) {
            if (feedList != null) {
                ui.setData(feedList!!.channel.items)
            } else ui.setData(feed.channel.items)
        }
        ui.showLoading(taskLoadNewsFeed.isRunning())
    }

    private fun handleLoadNews(
            data: LoadNewsFeedInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feedList = data.feed
        } else if (error != null) {
            throw Exception()
        }
        updateUi(FLAG_SETUP_UI)
    }

    private fun loadNewsFeedTask() =
            SingleResultTask<LoadNewsFeedInteractor.Param, LoadNewsFeedInteractor.Result, Unit>(
                    TASK_LOAD_NEWS_FEED,
                    { param: LoadNewsFeedInteractor.Param, _: Unit ->
                        loadNewsFeedInteractor.asRxSingle(param)
                                .observeOn(AndroidSchedulers.mainThread())
                    },
                    { result: LoadNewsFeedInteractor.Result, _: Unit ->
                        handleLoadNews(result, null)
                    },
                    { error: Throwable, _: Unit ->
                        handleLoadNews(null, error)
                    }
            )
}
