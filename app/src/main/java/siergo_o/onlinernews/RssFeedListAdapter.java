package siergo_o.onlinernews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private List<NewsItem> mRssFeedModels;
    private String imgUrl;
    private int feedPosition;
    private Context context;

    class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;


        }

    }

    RssFeedListAdapter(List<NewsItem> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item_layout, parent, false);
        return new FeedModelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FeedModelViewHolder holder, final int position) {
        final NewsItem rssFeedModel = mRssFeedModels.get(position);
        final ImageView newsImage = holder.rssFeedView.findViewById(R.id.imageview_1);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context holderContext = v.getContext();
                Log.e("MY", "ЧЕГОО??");
                Intent intent = new Intent(v.getContext(), DescriptionWebView.class);
                intent.putExtra("Url", mRssFeedModels.get(position).getUrl());
//                intent.putExtra("Description", mRssFeedModels.get(position).getDescription());
//                intent.putExtra("Title", mRssFeedModels.get(position).getTitle());
                holderContext.startActivity(intent);

            }
        });

//        holder.rssFeedView.setOnClickListener (new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        Picasso.get().load(rssFeedModel.getImgUrl()).into(newsImage);
        ((TextView) holder.rssFeedView.findViewById(R.id.textview_1)).setText(rssFeedModel.getTitle());
        ((TextView) holder.rssFeedView.findViewById(R.id.textview_2)).setText(rssFeedModel.getDate());
//        ((TextView) holder.rssFeedView.findViewById(R.id.textview_3)).setText(rssFeedModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }


}
