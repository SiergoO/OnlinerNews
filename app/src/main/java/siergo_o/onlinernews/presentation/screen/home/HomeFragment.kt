package siergo_o.onlinernews.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.App
import siergo_o.onlinernews.databinding.FragmentNewsBinding
import siergo_o.onlinernews.domain.news.interactor.LoadAllNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssFeed

class HomeFragment : BaseMvpFragment<HomeFragmentContract.Ui, HomeFragmentContract.Presenter.State, HomeFragmentContract.Presenter>(), HomeFragmentContract.Ui {

    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding: FragmentNewsBinding
        get() = _viewBinding!!
    private val tabTitles = listOf("Техно", "Люди", "Авто")
    private var pager: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            FragmentNewsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun createPresenterStateHolder(): PresenterStateHolder<HomeFragmentContract.Presenter.State> = VoidPresenterStateHolder()

    override fun getUi(): HomeFragmentContract.Ui = this

    override fun setData(news: List<RssFeed>) {
        pager = ViewPagerAdapter(this, news)
        viewBinding.viewpager.adapter = pager
        TabLayoutMediator(viewBinding.tablayout, viewBinding.viewpager) { tab, position ->
            val tag = tabTitles[position]
            viewBinding.viewpager.setCurrentItem(tab.position, true)
            tab.text = tag
        }.attach()
    }

    override fun createPresenter(): HomeFragmentContract.Presenter =
            HomeFragmentPresenter(LoadAllNewsInteractorImpl(App.instance.newsRepository))
}