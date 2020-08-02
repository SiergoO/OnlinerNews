package siergo_o.onlinernews.adapter;

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import siergo_o.onlinernews.R
import siergo_o.onlinernews.model.RssItem
import siergo_o.onlinernews.view.DescriptionWebView
import java.util.regex.Pattern

class PostsAdapter(private val context: Context?) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var imgUrl: String? = null
    private var posts: List<RssItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_list_item_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: RssItem? = posts?.get(position)
        val newsImage: ImageView = holder.image.findViewById(R.id.imageview_1)

        val imgUrlPattern = Pattern.compile("img src=\"(.*?)\"")
        val imgUrlMatcher = imgUrlPattern.matcher(post?.description!!)

        if (imgUrlMatcher.find()) {
            imgUrl = imgUrlMatcher.group(1)?.replace("thumbnail", "1400x5616") // this variable should contain the link image URL
        }
        Picasso.get().load(imgUrl).into(newsImage)
        holder.post.text = post.getTitle()
        holder.site.text = post.getPubDate()

        holder.itemView.setOnClickListener {
            val holderContext = it.context
            Log.e("MY", "ЧЕГОО??")
            val intent = Intent(holderContext, DescriptionWebView::class.java)
            intent.putExtra("Url", posts?.get(position)?.link)
            holderContext.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = posts?.size?:0

    fun set (posts: List<RssItem>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val post: TextView = itemView.findViewById(R.id.textview_1)
        val site: TextView = itemView.findViewById(R.id.textview_2)
        val image: ImageView = itemView.findViewById(R.id.imageview_1);
    }
}
