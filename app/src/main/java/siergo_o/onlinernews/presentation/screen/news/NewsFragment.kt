package siergo_o.onlinernews.presentation.screen.news;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import retrofit2.Retrofit
import siergo_o.onlinernews.App
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.data.rest.OnlinerApi
import siergo_o.onlinernews.databinding.FragmentNewsBinding
import siergo_o.onlinernews.domain.news.interactor.LoadNewsFeedInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.model.UiRssFeed
import siergo_o.onlinernews.presentation.model.toDomainModel
import siergo_o.onlinernews.presentation.model.toUiModel
import javax.inject.Inject
import javax.inject.Named

class NewsFragment :
        BaseMvpFragment<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State, NewsFragmentContract.Presenter>(), NewsFragmentContract.Ui {

    companion object {
        private const val ARG_CURRENT_TAB = "currentTab"
        private const val ARG_FEED_LIST = "tipsAmount"

        fun newInstance(tab: NewsFragmentContract.TAB, feed: RssFeed): NewsFragment =
                NewsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_CURRENT_TAB, tab)
                        putParcelable(ARG_FEED_LIST, feed.toUiModel())
                    }
                }
    }
    @Inject @field:[Named("tech")]
    lateinit var retrofitTech: Retrofit
    @Inject @field:[Named("people")]
    lateinit var retrofitPeople: Retrofit
    @Inject @field:[Named("auto")]
    lateinit var retrofitAuto: Retrofit
    private var postAdapter: NewsAdapter? = null
    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding: FragmentNewsBinding
        get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentNewsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postAdapter = NewsAdapter()
        viewBinding.apply {
            (root as SwipeRefreshLayout).setOnRefreshListener { presenter.newsRefreshed(requireArguments().getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB) }
            viewBinding.rvNews.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = postAdapter
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(posts: List<RssItem>) {
        postAdapter?.set(posts)
    }

    override fun showToast() {
        Toast.makeText(context, "Smth bad happened", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(isShown: Boolean) {
        (viewBinding.root as SwipeRefreshLayout).isRefreshing = isShown
    }

    override fun createPresenterStateHolder(): PresenterStateHolder<NewsFragmentContract.Presenter.State> =
            VoidPresenterStateHolder()

    override fun getUi(): NewsFragmentContract.Ui = this

    override fun createPresenter(): NewsFragmentContract.Presenter =
            NewsFragmentPresenter(
                    arguments?.getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB,
                    arguments?.getParcelable<UiRssFeed>(ARG_FEED_LIST)!!.toDomainModel(),
                    LoadNewsFeedInteractorImpl(NewsRepositoryImpl(retrofitTech.create(OnlinerApi::class.java),
                            retrofitPeople.create(OnlinerApi::class.java),
                            retrofitAuto.create(OnlinerApi::class.java))))
}