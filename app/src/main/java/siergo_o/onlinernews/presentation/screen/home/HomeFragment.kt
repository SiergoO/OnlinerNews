package siergo_o.onlinernews.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.R
import siergo_o.onlinernews.data.news.repository.NewsRepositoryImpl
import siergo_o.onlinernews.data.rest.OnlinerApiFactory
import siergo_o.onlinernews.databinding.FragmentHomeBinding
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.SearchNewsProvider
import siergo_o.onlinernews.presentation.utils.SimpleTextWatcher

class HomeFragment : BaseMvpFragment<HomeFragmentContract.Ui, HomeFragmentContract.Presenter.State, HomeFragmentContract.Presenter>(), HomeFragmentContract.Ui {

    private val api = OnlinerApiFactory()
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding: FragmentHomeBinding get() = _viewBinding!!
    private var searchNewsProvider: SearchNewsProvider? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            FragmentHomeBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun createPresenterStateHolder(): PresenterStateHolder<HomeFragmentContract.Presenter.State> = VoidPresenterStateHolder()

    override fun getUi(): HomeFragmentContract.Ui = this

    override fun setViewPager(news: List<RssFeed>) {
        viewBinding.layoutContent.viewpager.adapter = ViewPagerAdapter(this, news)
        TabLayoutMediator(viewBinding.layoutContent.tablayout, viewBinding.layoutContent.viewpager) { tab, position ->
            viewBinding.layoutContent.viewpager.setCurrentItem(tab.position, true)
            tab.text = when (position) {
                0 -> context?.getString(R.string.tech)
                1 -> context?.getString(R.string.people)
                2 -> context?.getString(R.string.auto)
                else -> ""
            }
        }.attach()
        viewBinding.layoutContent.buttonSearch.setOnClickListener {
            viewBinding.layoutContent.onlinerLogo.visibility = View.GONE
            viewBinding.layoutContent.searchNews.apply {
                addTextChangedListener(searchTextWatcher)
                visibility = View.VISIBLE
                requestFocus()
            }
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            viewBinding.apply {
                layoutLoading.root.visibility = View.VISIBLE
                layoutContent.root.visibility = View.GONE
            }
        } else viewBinding.apply {
            layoutLoading.root.visibility = View.GONE
            layoutContent.root.visibility = View.VISIBLE
        }
    }

    override fun createPresenter(): HomeFragmentContract.Presenter =
            HomeFragmentPresenter(requireContext(),
                    LoadAllNewsInteractorImpl(
                            NewsRepositoryImpl(
                                    api.getApi("tech"),
                                    api.getApi("people"),
                                    api.getApi("auto")))
            )

    private val searchTextWatcher = SimpleTextWatcher { text ->
        searchNewsProvider?.stateChanged(text.toString())
    }
}