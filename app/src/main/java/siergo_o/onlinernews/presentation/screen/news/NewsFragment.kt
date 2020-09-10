package siergo_o.onlinernews.presentation.screen.news;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import dagger.android.support.DaggerFragment
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.databinding.FragmentNewsBinding
import siergo_o.onlinernews.domain.news.interactor.LoadNewsFeedInteractorImpl
import siergo_o.onlinernews.domain.news.interactor.Search
import siergo_o.onlinernews.domain.news.interactor.SearchNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem
import siergo_o.onlinernews.presentation.model.UiRssFeed
import siergo_o.onlinernews.presentation.model.toDomainModel
import siergo_o.onlinernews.presentation.model.toUiModel
import javax.inject.Inject

class NewsFragment :
        DaggerFragment(), NewsFragmentContract.Ui {

    companion object {
        private const val ARG_CURRENT_TAB = "currentTab"

        fun newInstance(tab: NewsFragmentContract.TAB): NewsFragment =
                NewsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_CURRENT_TAB, tab)
                    }
                }
    }

    private var postAdapter: NewsAdapter? = null
    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding: FragmentNewsBinding
        get() = _viewBinding!!
    @Inject
    lateinit var newsFragmentPresenter: NewsFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentNewsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsFragmentPresenter.start(this)
        postAdapter = NewsAdapter()
        newsFragmentPresenter.setTab(arguments?.getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB)
        viewBinding.apply {
//          root.setOnRefreshListener { presenter.newsRefreshed(requireArguments().getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB) }
            rvNews.apply {
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

    override fun setStates(posts: List<RssItem>) {
        postAdapter?.set(posts)
    }

    override fun showToast() {
        Toast.makeText(context, "Smth bad happened", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        viewBinding.root.isRefreshing = show
    }
}