package siergo_o.onlinernews.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentAuto extends ListFragment {

    private RecyclerView recyclerViewAuto;
    private SwipeRefreshLayout swipeLayoutAuto;
    private List<NewsItem> FeedAutoList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.auto_news_fragment, container, false);

        recyclerViewAuto = rootView.findViewById(R.id.rv_auto);
        swipeLayoutAuto = rootView.findViewById(R.id.swipe_auto);

        recyclerViewAuto.setHasFixedSize(true);
        RssFeedListAdapter adapter = new RssFeedListAdapter(new ArrayList<NewsItem>());
        recyclerViewAuto.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAuto.setAdapter(adapter);

        new FetchFeedTask().execute((Void) null);

        swipeLayoutAuto.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
            swipeLayoutAuto.setRefreshing(true);
            urlLink = "https://auto.onliner.by/feed";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                RssData rssData = new RssData();
                FeedAutoList = rssData.parseFeed(inputStream);

                return true;
            } catch (IOException | XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            swipeLayoutAuto.setRefreshing(false);

            if (success) {
                recyclerViewAuto.setAdapter(new RssFeedListAdapter(FeedAutoList));
            } else {
                Log.e(TAG, "Error");
            }
        }
    }



}
