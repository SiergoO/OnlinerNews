package siergo_o.onlinernews.presentation.screen.news;

import com.ipictheaters.ipic.presentation.utils.task.MultiResultTask
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadThematicNewsInteractor
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.interactor.SearchNewsInteractor
import siergo_o.onlinernews.domain.news.model.Feed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.utils.ObservableValue
import siergo_o.onlinernews.presentation.utils.Result
import siergo_o.onlinernews.presentation.utils.asRxObservable
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(
        private val searchNewsInteractor: SearchNewsInteractor,
        private val loadThematicNewsInteractor: LoadThematicNewsInteractor,
        private val feed: Feed,
        private val search: Search
) : NewsFragmentContract.Presenter {

    companion object {
        private const val TASK_LOAD_THEMATIC_NEWS = "loadThematicNews"
        private const val TASK_SEARCH_NEWS = "searchNews"
    }

    private lateinit var ui: NewsFragment
    private val taskLoadNewsFeed = loadThematicNewsTask()
    private val taskSearchNews = createSearchNewsTask()
    private val searchQuery = ObservableValue("")
    private var news: List<RssItem> = listOf()
    private var index = 0

    override fun start(ui: NewsFragment) {
        this.ui = ui
        updateUi()
    }

    override fun newsRefreshed(tab: NewsFragmentContract.TAB) {
        taskLoadNewsFeed.start(LoadThematicNewsInteractor.Param(tab), Unit)
    }

    fun setCurrentTab(tab: NewsFragmentContract.TAB) {
        index = NewsFragmentContract.TAB.values().indexOf(tab)
        news = feed.feed[index]?.channel?.items ?: listOf()
        taskSearchNews.start(SearchNewsInteractor.Param(searchQuery, news), Unit)
        search.observe { searchQuery.set(it) }
        updateUi()
    }

    private fun updateUi() {
        ui.setData(news)
        ui.showLoading(taskLoadNewsFeed.isRunning())
    }

    private fun handleLoadNewsResult(
            data: LoadThematicNewsInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feed.feed[index] = data.feed
        } else if (error != null) {
            ui.showError(error.message.toString())
        }
        updateUi()
    }

    private fun loadThematicNewsTask() =
            SingleResultTask<LoadThematicNewsInteractor.Param, LoadThematicNewsInteractor.Result, Unit>(
                    TASK_LOAD_THEMATIC_NEWS,
                    { param: LoadThematicNewsInteractor.Param, _: Unit ->
                        loadThematicNewsInteractor.asRxSingle(param)
                                .observeOn(AndroidSchedulers.mainThread())
                    },
                    { result: LoadThematicNewsInteractor.Result, _: Unit ->
                        handleLoadNewsResult(result, null)
                    },
                    { error: Throwable, _: Unit ->
                        handleLoadNewsResult(null, error)
                    }
            )

    private fun handleSearchNewsResult(result: SearchNewsInteractor.Result) {
        if (result.search == searchQuery.get()) {
            news = when (result.result) {
                is Result.Success -> {
                    result.result.value
                }
                is Result.Error -> {
                    listOf()
                }
            }
            updateUi()
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
