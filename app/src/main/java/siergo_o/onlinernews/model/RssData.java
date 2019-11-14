package siergo_o.onlinernews.model;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siergo_o.onlinernews.NewsItem;

public class RssData {

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
