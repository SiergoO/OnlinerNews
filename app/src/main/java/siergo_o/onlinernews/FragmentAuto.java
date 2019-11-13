package siergo_o.onlinernews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                FeedAutoList = parseFeed(inputStream);

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

    public List<NewsItem> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {


        String title = null;
        String date = null;
        String url = null;
        String description = null;
        String imgUrl = null;
        boolean isItem = false;
        List<NewsItem> items = new ArrayList<>();
        Pattern urlPattern = Pattern.compile("href=\"(.*?)\"");
        Pattern imgUrlPattern = Pattern.compile("img src=\"(.*?)\"");
        Pattern descriptionPattern = Pattern.compile("<p>(.*?)</p>");

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("pubDate")) {
                    date = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }


                if (title != null && date != null && description != null) {
                    if (isItem) {

                        title = title.replaceAll("&nbsp;", " ");
                        date = date.replaceAll("[+]0300", "");

                        Matcher urlMatcher = urlPattern.matcher(description);

                        if (urlMatcher.find()) {
                            url = urlMatcher.group(1); // this variable should contain the link URL
                            Log.e("MY", url);
                        }


                        Matcher imgUrlMatcher = imgUrlPattern.matcher(description);

                        if (imgUrlMatcher.find()) {
                            imgUrl = imgUrlMatcher.group(1); // this variable should contain the link image URL
                            imgUrl = imgUrl.replace("thumbnail", "1400x5616");
                            Log.e("MY", imgUrl);
                        }

                        Matcher descriptionMatcher = descriptionPattern.matcher(description);


                        if (descriptionMatcher.find(2)) {
                            description = descriptionMatcher.group(1); // this variable should contain the link image URL
                            Log.e("MY", description);
                        }


                        NewsItem item = new NewsItem(title, date, description, url, imgUrl);
                        items.add(item);
                    }

                    title = null;
                    date = null;
                    description = null;
                    url = null;
                    imgUrl = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

}
