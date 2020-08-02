package siergo_o.onlinernews.view;

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import siergo_o.onlinernews.R
import siergo_o.onlinernews.adapter.PostsAdapter
import siergo_o.onlinernews.model.RssFeed
import siergo_o.onlinernews.model.RssItem
import siergo_o.onlinernews.model.RssService

class FragmentNewsHolder(private val urlLink: String) : ListFragment() {

    companion object {
        private val TAG = "MY";
    }

    private var recyclerView: RecyclerView? = null
    private val posts: List<RssItem>? = null
    private val context: Context? = null
    private var fragmentNews: Fragment? = null
    private val position: Int? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun getContext(): Context? = context

    private fun getUrlLink() = urlLink

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val rootView = inflater.inflate(R.layout.news_fragment, container, false)
        val itemView = inflater.inflate(R.layout.news_list_item_layout, container, false)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_container)
        recyclerView = rootView.findViewById(R.id.rv_news)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = PostsAdapter(posts, context)

        getParcedData()

        mSwipeRefreshLayout?.setOnRefreshListener { FetchFeedTask().execute() }

        val cvPeople: CardView = itemView.findViewById(R.id.cardview)
        cvPeople.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.viewpager, FragmentNewsHolder(urlLink))
            transaction.commit()
        }
        return rootView
    }

    @SuppressLint("StaticFieldLeak")
    inner class FetchFeedTask : AsyncTask<Void, Void, Boolean>() {

        private var urlLink: String? = null

        override fun onPreExecute() {
            mSwipeRefreshLayout!!.isRefreshing = true
            urlLink = getUrlLink()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            if (TextUtils.isEmpty(urlLink))
                return false
            getParcedData()
            return true
        }

        override fun onPostExecute(success: Boolean) {
            mSwipeRefreshLayout!!.isRefreshing = false
            if (success) {
                recyclerView?.adapter = PostsAdapter(posts, activity)
            } else {
                Log.e(TAG, "Error")
            }
        }
    }

    fun getParcedData() {

        val call: Call<RssFeed> = RssService.getInstance(this.getUrlLink()).onlinerApi.feed

        call.enqueue(object : Callback<RssFeed> {
            override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                if (call.isCanceled) {
                    println("Call was cancelled forcefully")
                } else {
                    println("Network Error :: " + t.localizedMessage)
                }
            }

            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                val posts = response.body()?.channel?.item
                val adapter = PostsAdapter(posts, activity)

                for (i in 0 until posts!!.size) {
                    Log.e("J", posts[i].toString())
                }
                if (response.isSuccessful) {
                    Log.e("123", posts[0].toString())

                } else {
                    Log.e("321", "????????????")
                }
                recyclerView!!.adapter = adapter
            }
        })
    }
}
