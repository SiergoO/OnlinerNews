package siergo_o.onlinernews.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siergo_o.onlinernews.R;
import siergo_o.onlinernews.model.RssItem;
import siergo_o.onlinernews.view.DescriptionWebView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<RssItem> posts;
    private String imgUrl;

    public Context getContext() {
        return this.context;
    }

    public PostsAdapter(List<RssItem> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        RssItem post = posts.get(position);
        final ImageView newsImage = holder.image.findViewById(R.id.imageview_1);

        Pattern imgUrlPattern = Pattern.compile("img src=\"(.*?)\"");
        Matcher imgUrlMatcher = imgUrlPattern.matcher(posts.get(position).getDescription());

        if (imgUrlMatcher.find()) {
            imgUrl= Objects.requireNonNull(imgUrlMatcher.group(1)).replace("thumbnail", "1400x5616"); // this variable should contain the link image URL
        }


        Picasso.get().load(imgUrl).into(newsImage);
        holder.post.setText(post.getTitle());
        holder.site.setText(post.getPubDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context holderContext = v.getContext();
                Log.e("MY", "ЧЕГОО??");
                Intent intent = new Intent(v.getContext(), DescriptionWebView.class);
                intent.putExtra("Url", posts.get(position).getLink());
                holderContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        if (posts == null){
            return 0;
        }
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView post,site;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.textview_1);
            site = itemView.findViewById(R.id.textview_2);
            image = itemView.findViewById(R.id.imageview_1);
        }
    }
}
