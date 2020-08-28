package siergo_o.onlinernews.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import siergo_o.onlinernews.R
import siergo_o.onlinernews.databinding.ItemNewsBinding
import siergo_o.onlinernews.domain.news.model.RssItem
import java.util.regex.Pattern

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    companion object {
        private const val ARG_POST_URL = "postUrl"
    }

    private var _viewBinding: ItemNewsBinding? = null
    private val viewBinding: ItemNewsBinding
        get() = _viewBinding!!
    private var imgUrl: String? = null
    private lateinit var posts: List<RssItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false).also { _viewBinding = it })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val imgUrlPattern = Pattern.compile("img src=\"(.*?)\"")
        val imgUrlMatcher = imgUrlPattern.matcher(post.description!!)
        if (imgUrlMatcher.find()) {
            imgUrl = imgUrlMatcher.group(1)?.replace("thumbnail", "1400x5616")
        }
        Picasso.get().load(imgUrl).into(holder.image)
        holder.apply {
            title.text = post.title
            date.text = post.pubDate
            itemView.setOnClickListener {
                it.findNavController().navigate(R.id.navToDescriptionWebView, bundleOf(ARG_POST_URL to post.link))
            }
        }
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
