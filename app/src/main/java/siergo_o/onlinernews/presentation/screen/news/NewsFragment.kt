package siergo_o.onlinernews.presentation.screen.news;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ipictheaters.ipic.presentation.base.BaseMvpFragment
import com.softeq.android.mvp.PresenterStateHolder
import com.softeq.android.mvp.VoidPresenterStateHolder
import siergo_o.onlinernews.R
import siergo_o.onlinernews.adapter.PostsAdapter
import siergo_o.onlinernews.data.rest.model.toDomainModel
import siergo_o.onlinernews.domain.news.model.RssFeed
import siergo_o.onlinernews.domain.news.model.RssItem

class NewsFragment(private val tab: NewsFragmentContract.TAB, private val feed: RssFeed) : BaseMvpFragment<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State, NewsFragmentContract.Presenter>(), NewsFragmentContract.Ui {

    private var recyclerView: RecyclerView? = null
    private var postAdapter: PostsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_news_content, container, false)
        recyclerView = rootView.findViewById(R.id.rv_news)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postAdapter = PostsAdapter(context)
        postAdapter!!.set(feed.channel.items!!.map { it.toDomainModel() })
        recyclerView!!.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = postAdapter
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(posts: List<RssItem>) {

    }

    override fun showToast() {
        Toast.makeText(context, "Smth bad happened", Toast.LENGTH_SHORT).show()
    }

    override fun createPresenterStateHolder(): PresenterStateHolder<NewsFragmentContract.Presenter.State> =
            VoidPresenterStateHolder()

    override fun getUi(): NewsFragmentContract.Ui = this

    override fun createPresenter(): NewsFragmentContract.Presenter =
            NewsFragmentPresenter(tab)
}