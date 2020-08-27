package siergo_o.onlinernews.presentation.screen.news;

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractor
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(private val tab: NewsFragmentContract.TAB) : BaseMvpPresenter<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State>(), NewsFragmentContract.Presenter {

    companion object {
        private const val FLAG_SETUP_UI = 0x0001
    }
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
    }

    override fun newsRefreshed() {

    }
}
