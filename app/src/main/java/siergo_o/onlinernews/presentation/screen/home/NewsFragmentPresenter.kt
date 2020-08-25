package siergo_o.onlinernews.presentation.screen.home;

import com.ipictheaters.ipic.presentation.base.BaseMvpPresenter
import com.ipictheaters.ipic.presentation.utils.task.SingleResultTask
import io.reactivex.android.schedulers.AndroidSchedulers
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractor
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.utils.asRxSingle

class NewsFragmentPresenter(private val loadNewsInteractor: LoadNewsInteractor) : BaseMvpPresenter<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State>(), NewsFragmentContract.Presenter {

    companion object {
        private const val TAG = "DiningChecksPaymentResultFragmentPresenter"
        private const val FLAG_SETUP_UI = 0x0001
        private const val TASK_LOAD_NEWS = "sendDiningCheckReceipt"
    }

    override fun start() {
        taskLoadNews.start(LoadNewsInteractor.Param(), Unit)
    }

    private val taskLoadNews = loadNewsTask()
    private var feedList: List<RssItem>? = null

    private fun updateUi(flags: Int) {
        if (0 != (flags and FLAG_SETUP_UI)) {
            ui.setData(feedList ?: emptyList())
        }
    }

//    override fun getParcedData(link: String) {
//
//        val call: Call<NetRssFeed?> = RssService.getInstance(link).onlinerApi.feed!!
//
//        call.enqueue(object : Callback<NetRssFeed?> {
//            override fun onFailure(call: Call<NetRssFeed?>, t: Throwable) {
//                if (call.isCanceled) {
//                    println("Call was cancelled forcefully")
//                } else {
//                    Log.e("KK", t.toString())
//                }
//            }
//
//            override fun onResponse(call: Call<NetRssFeed?>, response: Response<NetRssFeed?>) {
//                val posts = response.body()?.channel?.items ?: listOf()
//                for (element in posts) {
//                    Log.e("J", element.toString())
//                }
//                if (response.isSuccessful) {
//                    ui.setData(posts)
//                    Log.e("123", posts[0].toString())
//
//                } else {
//                    Log.e("321", "????????????")
//                }
//            }
//        })
//    }

    private fun handleDiningCheckReceipt(
            data: LoadNewsInteractor.Result?,
            error: Throwable?
    ) {
        if (data != null) {
            feedList = data.rssFeed.channel?.items?.map { it.toDomainModel() }
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
