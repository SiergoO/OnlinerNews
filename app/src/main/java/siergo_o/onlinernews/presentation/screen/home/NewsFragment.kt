package siergo_o.onlinernews.presentation.screen.home;

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import siergo_o.onlinernews.data.rest.model.NetRssItem
import siergo_o.onlinernews.domain.news.interactor.LoadNewsInteractorImpl
import siergo_o.onlinernews.domain.news.model.RssItem

class NewsFragment(private val urlLink: String) : BaseMvpFragment<NewsFragmentContract.Ui, NewsFragmentContract.Presenter.State, NewsFragmentContract.Presenter>(), NewsFragmentContract.Ui {

    companion object {
        private val TAG = "MY"
    }

    private var recyclerView: RecyclerView? = null
    private val posts: List<NetRssItem>? = null
    private var fragmentNews: Fragment? = null
    private val position: Int? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var postAdapter: PostsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val rootView = inflater.inflate(R.layout.news_fragment, container, false)
        val itemView = inflater.inflate(R.layout.news_list_item_layout, container, false)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_container)
        recyclerView = rootView.findViewById(R.id.rv_news)
//        mSwipeRefreshLayout?.setOnRefreshListener { FetchFeedTask().execute() }

        val cvPeople: CardView = itemView.findViewById(R.id.cardview)
        cvPeople.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.viewpager, NewsFragment(urlLink))
            transaction.commit()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postAdapter = PostsAdapter(context)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = postAdapter
//        presenter.getParcedData(urlLink)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData(posts: List<RssItem>) {
        postAdapter?.set(posts)
    }

    override fun showToast() {
        Toast.makeText(context, "Smth bad happened", Toast.LENGTH_SHORT).show()
    }

//    @SuppressLint("StaticFieldLeak")
//    inner class FetchFeedTask : AsyncTask<Void, Void, Boolean>() {
//
//        private var urlLink: String? = null
//
//        override fun onPreExecute() {
//            mSwipeRefreshLayout!!.isRefreshing = true
//            this.urlLink = this@NewsFragment.urlLink
//        }
//
//        override fun doInBackground(vararg params: Void?): Boolean {
//            if (TextUtils.isEmpty(urlLink))
//                return false
////            presenter.getParcedData(urlLink!!)
//            return true
//        }
//
//        override fun onPostExecute(success: Boolean) {
//            mSwipeRefreshLayout!!.isRefreshing = false
//            if (success) {
//                recyclerView?.adapter = postAdapter // TODO adapter
//            } else {
//                Log.e(TAG, "Error")
//            }
//        }
//    }

    override fun createPresenterStateHolder(): PresenterStateHolder<NewsFragmentContract.Presenter.State> =
            VoidPresenterStateHolder()

    override fun getUi(): NewsFragmentContract.Ui = this

    override fun createPresenter(): NewsFragmentContract.Presenter =
            NewsFragmentPresenter(LoadNewsInteractorImpl(App.instance.newsRepository))
}