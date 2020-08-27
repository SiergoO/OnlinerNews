package siergo_o.onlinernews.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.App
import siergo_o.onlinernews.R
import siergo_o.onlinernews.databinding.FragmentNewsBinding
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.ViewPagerAdapter

class HomeFragment : BaseMvpFragment<HomeFragmentContract.Ui, HomeFragmentContract.Presenter.State, HomeFragmentContract.Presenter>(), HomeFragmentContract.Ui {

    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding: FragmentNewsBinding
        get() = _viewBinding!!
    private val tabTitles = listOf("Техно", "Люди", "Авто")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            FragmentNewsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun createPresenterStateHolder(): PresenterStateHolder<HomeFragmentContract.Presenter.State> = VoidPresenterStateHolder()

    override fun getUi(): HomeFragmentContract.Ui = this

    override fun showLoading(isShown: Boolean) {
    }

    override fun setData(news: List<RssFeed>) {
        viewBinding.viewpager.adapter = ViewPagerAdapter(this, news)
        TabLayoutMediator(viewBinding.tablayout, viewBinding.viewpager) { tab, position ->
            val tag = tabTitles[position]
            viewBinding.viewpager.setCurrentItem(tab.position, true)
            tab.text = tag
        }.attach()
    }

    override fun createPresenter(): HomeFragmentContract.Presenter =
            HomeFragmentPresenter(LoadNewsInteractorImpl(App.instance.newsRepository))
}