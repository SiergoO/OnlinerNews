package siergo_o.onlinernews.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.App
import siergo_o.onlinernews.R
import siergo_o.onlinernews.databinding.FragmentHomeBinding
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed

class HomeFragment : BaseMvpFragment<HomeFragmentContract.Ui, HomeFragmentContract.Presenter.State, HomeFragmentContract.Presenter>(), HomeFragmentContract.Ui {

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding: FragmentHomeBinding get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            FragmentHomeBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun createPresenterStateHolder(): PresenterStateHolder<HomeFragmentContract.Presenter.State> = VoidPresenterStateHolder()

    override fun getUi(): HomeFragmentContract.Ui = this

    override fun setViewPager(news: List<RssFeed>) {
        viewBinding.viewpager.adapter = ViewPagerAdapter(this, news)
        TabLayoutMediator(viewBinding.tablayout, viewBinding.viewpager) { tab, position ->
            viewBinding.viewpager.setCurrentItem(tab.position, true)
            tab.text = when(position) {
                0 -> context?.getString(R.string.tech)
                1 -> context?.getString(R.string.people)
                2 -> context?.getString(R.string.auto)
                else -> ""
            }
        }.attach()
    }

    override fun createPresenter(): HomeFragmentContract.Presenter =
            HomeFragmentPresenter(LoadAllNewsInteractorImpl(App.instance.newsRepository))
}