package siergo_o.onlinernews.presentation.screen.news;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import siergo_o.onlinernews.databinding.FragmentNewsBinding
import siergo_o.onlinernews.domain.news.model.RssItem
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

    @Inject
    lateinit var newsFragmentPresenter: NewsFragmentPresenter
    @Inject
    lateinit var newsAdapter: NewsAdapter
    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding: FragmentNewsBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentNewsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsFragmentPresenter.apply {
            start(this@NewsFragment)
            setCurrentTab(arguments?.getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB)
        }
        viewBinding.apply {
            root.setOnRefreshListener { newsFragmentPresenter.newsRefreshed(requireArguments().getSerializable(ARG_CURRENT_TAB) as NewsFragmentContract.TAB) }
            rvNews.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = newsAdapter
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(posts: List<RssItem>) {
        newsAdapter.set(posts)
    }

    override fun setStates(posts: List<RssItem>) {
        newsAdapter.set(posts)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(show: Boolean) {
        viewBinding.root.isRefreshing = show
    }
}