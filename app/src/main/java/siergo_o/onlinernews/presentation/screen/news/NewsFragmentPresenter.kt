package siergo_o.onlinernews.presentation.screen.news;

import siergo_o.onlinernews.presentation.utils.MultiResultTask
import siergo_o.onlinernews.presentation.utils.SingleResultTask
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
        taskLoadNewsFeed.start(LoadThematicNewsInteractor.Param(tab))
    }

    fun setCurrentTab(tab: NewsFragmentContract.TAB) {
        index = NewsFragmentContract.TAB.values().indexOf(tab)
        news = feed.feed[index]?.channel?.items ?: listOf()
        taskSearchNews.start(SearchNewsInteractor.Param(searchQuery, news))
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
        when {
            data != null -> feed.feed[index] = data.feed
            error != null -> ui.showError(error.message.toString())
        }
        updateUi()
    }

    private fun loadThematicNewsTask() =
        SingleResultTask<LoadThematicNewsInteractor.Param, LoadThematicNewsInteractor.Result>(
            { param: LoadThematicNewsInteractor.Param ->
                loadThematicNewsInteractor.asRxSingle(param)
                    .observeOn(AndroidSchedulers.mainThread())
            },
            { result: LoadThematicNewsInteractor.Result ->
                handleLoadNewsResult(result, null)
            },
            { error: Throwable ->
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
        MultiResultTask<SearchNewsInteractor.Param, SearchNewsInteractor.Result>(
            { param ->
                searchNewsInteractor.asRxObservable(param)
                    .observeOn(AndroidSchedulers.mainThread())
            },
            { data ->
                handleSearchNewsResult(data)
            })
}
