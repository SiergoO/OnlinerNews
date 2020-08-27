package siergo_o.onlinernews.presentation.screen.home;

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(private val tab: NewsFragmentContract.TAB, private val loadNewsInteractor: LoadNewsInteractor) : BaseMvpPresenter<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State>(), NewsFragmentContract.Presenter {

    companion object {
        private const val TAG = "DiningChecksPaymentResultFragmentPresenter"
        private const val FLAG_SETUP_UI = 0x0001
        private const val TASK_LOAD_NEWS = "sendDiningCheckReceipt"
    }

    override fun start() {
        taskLoadNews.start(LoadNewsInteractor.Param(), Unit)
    }

    override fun newsRefreshed() {
        taskLoadNews.start(LoadNewsInteractor.Param(), Unit)
        updateUi(0)
    }

    private val taskLoadNews = loadNewsTask()
    private var feedList: List<RssFeed>? = null

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_UI)) {
            if (feedList != null) {
                ui.setData(
                        when (tab) {
                            NewsFragmentContract.TAB.TECH -> feedList!![0].channel.items!!.map { it.toDomainModel() }
                            NewsFragmentContract.TAB.PEOPLE -> feedList!![1].channel.items!!.map { it.toDomainModel() }
                            NewsFragmentContract.TAB.AUTO -> feedList!![2].channel.items!!.map { it.toDomainModel() }
                        }
                )
            }
        }
        ui.showLoading(taskLoadNews.isRunning())
    }

    private fun handleDiningCheckReceipt(
            data: LoadNewsInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feedList = data.feed
        } else if (error != null) {
            ui.showToast()
        }
        updateUi(FLAG_SETUP_UI)
    }

    private fun loadNewsTask() =
            SingleResultTask<LoadNewsInteractor.Param, LoadNewsInteractor.Result, Unit>(
                    TASK_LOAD_NEWS,
                    { param: LoadNewsInteractor.Param, _: Unit ->
                        loadNewsInteractor.asRxSingle(param)
                                .observeOn(AndroidSchedulers.mainThread())
                    },
                    { result: LoadNewsInteractor.Result, _: Unit ->
                        handleDiningCheckReceipt(result, null)
                    },
                    { error: Throwable, _: Unit ->
                        handleDiningCheckReceipt(null, error)
                    }
            )
}
