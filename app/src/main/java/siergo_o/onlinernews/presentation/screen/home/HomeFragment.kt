package siergo_o.onlinernews.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import siergo_o.onlinernews.R
import siergo_o.onlinernews.databinding.FragmentHomeBinding
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.presentation.screen.BaseFragment
import siergo_o.onlinernews.presentation.utils.SimpleTextWatcher
import javax.inject.Inject

class HomeFragment : DaggerFragment(), BaseFragment, HomeFragmentContract.Ui {

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding: FragmentHomeBinding get() = _viewBinding!!
    @Inject
    lateinit var homeFragmentPresenter: HomeFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            FragmentHomeBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentPresenter.start(this)
    }

    override fun setViewPager(news: List<RssFeed>) {
        viewBinding.layoutContent.apply {
            viewpager.adapter = ViewPagerAdapter(this@HomeFragment)
            TabLayoutMediator(this.tablayout, this.viewpager) { tab, position ->
                viewBinding.layoutContent.viewpager.setCurrentItem(tab.position, true)
                tab.text = when (position) {
                    0 -> context?.getString(R.string.tech)
                    1 -> context?.getString(R.string.people)
                    2 -> context?.getString(R.string.auto)
                    else -> ""
                }
            }.attach()
            buttonSearch.setOnClickListener {
                this.apply {
                    onlinerLogo.visibility = View.GONE
                    searchNews.apply {
                        addTextChangedListener(searchTextWatcher)
                        visibility = View.VISIBLE
                        requestFocus()
                    }
                }
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

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private val searchTextWatcher = SimpleTextWatcher { text ->
        homeFragmentPresenter.search(text.toString())
    }
}