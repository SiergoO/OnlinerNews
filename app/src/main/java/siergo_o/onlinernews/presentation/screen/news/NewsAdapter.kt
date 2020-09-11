package siergo_o.onlinernews.presentation.screen.news;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import siergo_o.onlinernews.R
import siergo_o.onlinernews.databinding.ItemNewsBinding
import siergo_o.onlinernews.domain.news.model.RssItem
import java.lang.Exception
import java.util.regex.Pattern

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    companion object {
        private const val ARG_POST_URL = "postUrl"
    }

    private var _viewBinding: ItemNewsBinding? = null
    private val viewBinding: ItemNewsBinding
        get() = _viewBinding!!
    private var posts: List<RssItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false).also { _viewBinding = it })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.itemView.visibility = View.GONE
        Picasso.get().load(post.imageUrl.replace("thumbnail", "1400x5616")).into(holder.image, object : Callback {
            override fun onSuccess() {
                holder.apply {
                    title.text = post.title
                    date.text = post.pubDate
                    itemView.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            it.findNavController().navigate(R.id.navToDescriptionWebView, bundleOf(ARG_POST_URL to post.link))
                        }
                    }
                }
            }
            override fun onError(e: Exception?) {
                Picasso.get().load(R.drawable.logo_onliner).into(holder.image)
            }
        })
    }

    override fun getItemCount(): Int = posts.size

    fun set(posts: List<RssItem>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: ItemNewsBinding) : RecyclerView.ViewHolder(item.root) {
        val title: TextView = viewBinding.postTitle
        val date: TextView = viewBinding.postDate
        val image: ImageView = viewBinding.postImage
    }
}
