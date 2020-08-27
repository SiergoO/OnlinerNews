package siergo_o.onlinernews.presentation.screen.home;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.App
import siergo_o.onlinernews.R
import siergo_o.onlinernews.adapter.PostsAdapter
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssItem

class NewsFragment (private val tab: NewsFragmentContract.TAB) : BaseMvpFragment<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State, NewsFragmentContract.Presenter>(), NewsFragmentContract.Ui {

    companion object {
        private val TAG = "MY"
    }

    private var recyclerView: RecyclerView? = null
    private var fragmentNews: Fragment? = null
    private val position: Int? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var postAdapter: PostsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val rootView = inflater.inflate(R.layout.fragment_news, container, false)
        val itemView = inflater.inflate(R.layout.item_news, container, false)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_container)
        recyclerView = rootView.findViewById(R.id.rv_news)
        mSwipeRefreshLayout?.setOnRefreshListener { presenter.newsRefreshed() }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postAdapter = PostsAdapter(context)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = postAdapter

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(posts: List<RssItem>) {
        postAdapter?.set(posts)
    }

    override fun showLoading(isShown: Boolean) {
        mSwipeRefreshLayout?.isRefreshing = isShown
    }

    override fun showToast() {
        Toast.makeText(context, "Smth bad happened", Toast.LENGTH_SHORT).show()
    }

    override fun createPresenterStateHolder(): PresenterStateHolder<NewsFragmentContract.Presenter.State> =
            VoidPresenterStateHolder()

    override fun getUi(): NewsFragmentContract.Ui = this

    override fun createPresenter(): NewsFragmentContract.Presenter =
            NewsFragmentPresenter(
                    tab,
                    LoadNewsInteractorImpl(App.instance.newsRepository))
}