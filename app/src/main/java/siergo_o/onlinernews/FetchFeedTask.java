//package siergo_o.onlinernews;
//
//import android.os.AsyncTask;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.Xml;
//import android.view.View;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {
//
//    private List<NewsItem> mFeedModelList;
//    private ;
//    private RecyclerView mRecyclerView;
//
//    private static final String TAG = "MY";
//    private String urlLink;
//
//    @Override
//    protected void onPreExecute() {
//        View view = FragmentAuto.
//        mSwipeLayout = mSwipeLayout.findViewById(R.id.swipe_auto);
//        mSwipeLayout.setRefreshing(true);
//        urlLink = "https://people.onliner.by/feed";
//    }
//
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//        if (TextUtils.isEmpty(urlLink))
//            return false;
//
//        try {
//
//            URL url = new URL(urlLink);
//            InputStream inputStream = url.openConnection().getInputStream();
//            mFeedModelList = parseFeed(inputStream);
//
//            return true;
//        } catch (IOException | XmlPullParserException e) {
//            Log.e(TAG, "Error", e);
//        }
//        return false;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean success) {
//        mSwipeLayout.setRefreshing(false);
//
//        if (success) {
//            mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
//        } else {
//            Log.e(TAG, "Error");
//        }
//
//
//    }
//
//
//    public List<NewsItem> parseFeed(InputStream inputStream) throws XmlPullParserException,
//            IOException {
//
//
//        String title = null;
//        String date = null;
//        String url = null;
//        String description = null;
//        String imgUrl = null;
//        boolean isItem = false;
//        List<NewsItem> items = new ArrayList<>();
//        Pattern urlPattern = Pattern.compile("href=\"(.*?)\"");
//        Pattern imgUrlPattern = Pattern.compile("img src=\"(.*?)\"");
//        Pattern descriptionPattern = Pattern.compile("<p>(.*?)</p>");
//
//        try {
//            XmlPullParser xmlPullParser = Xml.newPullParser();
//            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            xmlPullParser.setInput(inputStream, null);
//
//            xmlPullParser.nextTag();
//            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
//                int eventType = xmlPullParser.getEventType();
//
//                String name = xmlPullParser.getName();
//                if (name == null)
//                    continue;
//
//                if (eventType == XmlPullParser.END_TAG) {
//                    if (name.equalsIgnoreCase("item")) {
//                        isItem = false;
//                    }
//                    continue;
//                }
//
//                if (eventType == XmlPullParser.START_TAG) {
//                    if (name.equalsIgnoreCase("item")) {
//                        isItem = true;
//                        continue;
//                    }
//                }
//
//                Log.d("MyXmlParser", "Parsing name ==> " + name);
//                String result = "";
//                if (xmlPullParser.next() == XmlPullParser.TEXT) {
//                    result = xmlPullParser.getText();
//                    xmlPullParser.nextTag();
//                }
//
//                if (name.equalsIgnoreCase("title")) {
//                    title = result;
//                } else if (name.equalsIgnoreCase("pubDate")) {
//                    date = result;
//                } else if (name.equalsIgnoreCase("description")) {
//                    description = result;
//                }
//
//
//                if (title != null && date != null && description != null) {
//                    if (isItem) {
//
//                        title = title.replaceAll("&nbsp;", " ");
//                        date = date.replaceAll("[+]0300", "");
//
//                        Matcher urlMatcher = urlPattern.matcher(description);
//
//                        if (urlMatcher.find()) {
//                            url = urlMatcher.group(1); // this variable should contain the link URL
//                            Log.e("MY", url);
//                        }
//
//
//                        Matcher imgUrlMatcher = imgUrlPattern.matcher(description);
//
//                        if (imgUrlMatcher.find()) {
//                            imgUrl = imgUrlMatcher.group(1); // this variable should contain the link image URL
//                            imgUrl = imgUrl.replace("thumbnail", "1400x5616");
//                            Log.e("MY", imgUrl);
//                        }
//
//                        Matcher descriptionMatcher = descriptionPattern.matcher(description);
//
//
//                        if (descriptionMatcher.find(2)) {
//                            description = descriptionMatcher.group(1); // this variable should contain the link image URL
//                            Log.e("MY", description);
//                        }
//
//
//                        NewsItem item = new NewsItem(title, date, description, url, imgUrl);
//                        items.add(item);
//                    }
//
//                    title = null;
//                    date = null;
//                    description = null;
//                    url = null;
//                    imgUrl = null;
//                    isItem = false;
//                }
//            }
//
//            return items;
//        } finally {
//            inputStream.close();
//        }
//    }
//}