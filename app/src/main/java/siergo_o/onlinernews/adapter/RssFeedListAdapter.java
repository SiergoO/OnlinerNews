//package siergo_o.onlinernews.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//import siergo_o.onlinernews.R;
//import siergo_o.onlinernews.view.DescriptionWebView;
//
//public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {
//
//    private List<NewsItem> mRssFeedModels;
//    private String imgUrl;
//    private int feedPosition;
//    private Context context;
//
//    class FeedModelViewHolder extends RecyclerView.ViewHolder {
//        private View rssFeedView;
//
//        FeedModelViewHolder(View v) {
//            super(v);
//            rssFeedView = v;
//
//
//        }
//
//    }
//
//    public RssFeedListAdapter(List<NewsItem> rssFeedModels) {
//        mRssFeedModels = rssFeedModels;
//    }
//
//    @Override
//    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.news_list_item_layout, parent, false);
//        return new FeedModelViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(final FeedModelViewHolder holder, final int position) {
//        final NewsItem rssFeedModel = mRssFeedModels.get(position);
//        final ImageView newsImage = holder.rssFeedView.findViewById(R.id.imageview_1);
//
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Context holderContext = v.getContext();
//                Log.e("MY", "ЧЕГОО??");
//                Intent intent = new Intent(v.getContext(), DescriptionWebView.class);
//                intent.putExtra("Url", mRssFeedModels.get(position).getUrl());
//                holderContext.startActivity(intent);
//
//            }
//        });
//
//
//        Picasso.get().load(rssFeedModel.getImgUrl()).into(newsImage);
//        ((TextView) holder.rssFeedView.findViewById(R.id.textview_1)).setText(rssFeedModel.getTitle());
//        ((TextView) holder.rssFeedView.findViewById(R.id.textview_2)).setText(rssFeedModel.getDate());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mRssFeedModels.size();
//    }
//
//
//}
