package siergo_o.onlinernews.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import siergo_o.onlinernews.NewsItem;
import siergo_o.onlinernews.R;
import siergo_o.onlinernews.RssFeedListAdapter;
import siergo_o.onlinernews.model.RssData;

public class FragmentTech extends ListFragment {

    private RecyclerView recyclerViewTech;
    private SwipeRefreshLayout swipeLayoutTech;
    private List<NewsItem> FeedTechList;
    Fragment fragmentTech;
    CardView cvTech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.auto_news_fragment, container, false);
        View itemView = inflater.inflate(R.layout.news_list_item_layout, container, false);

        recyclerViewTech = rootView.findViewById(R.id.rv_auto);
        swipeLayoutTech = rootView.findViewById(R.id.swipe_auto);
        cvTech = itemView.findViewById(R.id.cardview);

        recyclerViewTech.setHasFixedSize(true);
        RssFeedListAdapter adapter = new RssFeedListAdapter(new ArrayList<NewsItem>());
        recyclerViewTech.setAdapter(adapter);
        recyclerViewTech.setLayoutManager(new LinearLayoutManager(getActivity()));

        new FetchFeedTask().execute((Void) null);

        cvTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTech = new FragmentAuto();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.viewpager, fragmentTech);
                transaction.commit();
            }
        });

        swipeLayoutTech.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });

        return rootView;
    }

    class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private static final String TAG = "MY";
        private String urlLink;

        @Override
        protected void onPreExecute() {
            swipeLayoutTech.setRefreshing(true);
            urlLink = "https://tech.onliner.by/feed";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                RssData rssData = new RssData();
                FeedTechList = rssData.parseFeed(inputStream);

                return true;
            } catch (IOException | XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            swipeLayoutTech.setRefreshing(false);

            if (success) {
                recyclerViewTech.setAdapter(new RssFeedListAdapter(FeedTechList));
            } else {
                Log.e(TAG, "Error");
            }


        }
    }


}
