package siergo_o.onlinernews.presentation.screen.home

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.utils.asRxSingle

class HomeFragmentPresenter(
        private val loadAllNewsInteractor: LoadAllNewsInteractor
) : BaseMvpPresenter<HomeFragmentContract.Ui, HomeFragmentContract.Presenter.State>(), HomeFragmentContract.Presenter {

    companion object {
        private const val FLAG_SETUP_HOME_UI = 0x0001
        private const val FLAG_SETUP_SPLASH_UI = 0x0002
        private const val TASK_LOAD_NEWS = "loadNews"
    }

    private val taskLoadNews = loadNewsTask()
    private var feedList: List<RssFeed>? = null

    override fun start() {
        updateUi(FLAG_SETUP_SPLASH_UI)
        taskLoadNews.start(LoadAllNewsInteractor.Param(), Unit)
    }

    override fun newsRefreshed() {
        taskLoadNews.start(LoadAllNewsInteractor.Param(), Unit)
    }

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_HOME_UI)) {
            if (feedList != null) {
                ui.setViewPager(feedList!!)
                ui.showLoading(false)
            }
        }
        if (0 != (flags and FLAG_SETUP_SPLASH_UI)) {
            ui.showLoading(true)
        }
    }

    private fun handleLoadNews(
            data: LoadAllNewsInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feedList = data.feed
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