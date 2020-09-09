package siergo_o.onlinernews.presentation.screen.news;

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.MultiResultTask
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadNewsFeedInteractor
import siergo_o.onlinernews.domain.news.interactor.SearchNewsInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.screen.SearchNewsProvider
import siergo_o.onlinernews.presentation.screen.home.HomeFragmentPresenter
import siergo_o.onlinernews.presentation.utils.ObservableValue
import siergo_o.onlinernews.presentation.utils.Result
import siergo_o.onlinernews.presentation.utils.asRxObservable
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(
        private val tab: NewsFragmentContract.TAB,
        private val feed: RssFeed,
        private val searchNewsInteractor: SearchNewsInteractor,
        private val loadNewsFeedInteractor: LoadNewsFeedInteractor) :
        BaseMvpPresenter<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State>(), NewsFragmentContract.Presenter, SearchNewsProvider {

    companion object {
        private const val FLAG_SETUP_UI = 0x0001
        private const val FLAG_SET_STATES = 0x0002
        private const val TASK_LOAD_NEWS_FEED = "sendDiningCheckReceipt"
        private const val TASK_SEARCH_NEWS = "loadNews"
        private val EMPTY_STATE_LIST = listOf<RssItem>()
    }

    override fun start() {
        updateUi(FLAG_SETUP_UI)
    }

    private var feedList: RssFeed? = null
    private val taskLoadNewsFeed = loadNewsFeedTask()
    private var news: List<RssItem>? = null
    private val searchQuery = ObservableValue("")
    private val taskSearchNews = createSearchNewsTask()

    override fun newsRefreshed(tab: NewsFragmentContract.TAB) {
        taskLoadNewsFeed.start(LoadNewsFeedInteractor.Param(tab), Unit)
    }

    override fun stateChanged(query: String) {
        this.searchQuery.set(query)
        taskSearchNews.start(SearchNewsInteractor.Param(searchQuery, feedList!!.channel.items), Unit)
    }

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_UI)) {
            if (feedList != null) {
                ui.setData(feedList!!.channel.items)
            } else ui.setData(feed.channel.items)
        }
        if (0 != (flags and FLAG_SET_STATES)) {
            ui.setStates(news?: EMPTY_STATE_LIST)
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

    private fun handleSearchNewsResult(result: SearchNewsInteractor.Result) {
        if (result.search == searchQuery.get()) {
            news = when (result.result) {
                is Result.Success -> {
                    result.result.value
                }
                is Result.Error -> {
                    EMPTY_STATE_LIST
                }
            }
            updateUi(FLAG_SET_STATES)
        }
    }

    private fun createSearchNewsTask() =
            MultiResultTask<SearchNewsInteractor.Param, SearchNewsInteractor.Result, Unit>(
                    TASK_SEARCH_NEWS,
                    { param, _ ->
                        searchNewsInteractor.asRxObservable(param)
                                .observeOn(AndroidSchedulers.mainThread())
                    },
                    { data, _ ->
                        handleSearchNewsResult(data)
                    },
                    { error, _ ->
//                        log.e(error, "Failed to search states. Actually, this should never happens")
                    })
}
