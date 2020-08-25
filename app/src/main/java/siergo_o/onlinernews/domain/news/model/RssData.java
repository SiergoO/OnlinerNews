//package siergo_o.onlinernews.model;
//
//import android.util.Log;
//import android.util.Xml;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import siergo_o.onlinernews.NewsItem;
//
//public class RssData {
//
//    public List<NewsItem> parseFeed(InputStream inputStream) throws XmlPullParserException,
//            IOException {
//
//        boolean isItem = false;
//
//        List<NewsItem> items = new ArrayList<>();
//        Pattern urlPattern = Pattern.compile("href=\"(.*?)\"");
//        Pattern imgUrlPattern = Pattern.compile("img src=\"(.*?)\"");
//        Pattern descriptionPattern = Pattern.compile("<p>(.*?)</p>");
//
//        try {
//            NewsItem newsItem = new NewsItem();
//            RssFeed rssFeed = new RssFeed();
//            XmlPullParser xmlPullParser = Xml.newPullParser();
//            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            xmlPullParser.setInput(inputStream, null);
//
//            xmlPullParser.nextTag();
//            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
//                int eventType = xmlPullParser.getEventType();
//
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
//
//                if (name.equalsIgnoreCase("title")) {
//                    newsItem.setTitle(result);
//                } else if (name.equalsIgnoreCase("pubDate")) {
//                    newsItem.setDate(result);
//                } else if (name.equalsIgnoreCase("description")) {
//                    newsItem.setDescription(result);
//
//                }
//
//
//                if (newsItem.getTitle() != null && newsItem.getDate() != null && newsItem.getDescription() != null) {
//                    if (isItem) {
//
//                        newsItem.setTitle(newsItem.getTitle().replaceAll("&nbsp;", " "));
//                        newsItem.setDate(newsItem.getDate().replaceAll("[+]0300", ""));
//
//                        Matcher urlMatcher = urlPattern.matcher(newsItem.getDescription());
//
//                        if (urlMatcher.find()) {
//                            newsItem.setUrl(urlMatcher.group(1)); // this variable should contain the link URL
//                            Log.e("MY", newsItem.getUrl());
//                        }
//
//
//                        Matcher imgUrlMatcher = imgUrlPattern.matcher(newsItem.getDescription());
//
//                        if (imgUrlMatcher.find()) {
//                            newsItem.setImgUrl(imgUrlMatcher.group(1)); // this variable should contain the link image URL
//                            newsItem.setImgUrl(newsItem.getImgUrl().replace("thumbnail", "1400x5616"));
//                            Log.e("MY", newsItem.getImgUrl());
//                        }
//
//                        Matcher descriptionMatcher = descriptionPattern.matcher(newsItem.getDescription());
//
//
//                        if (descriptionMatcher.find(2)) {
//                            newsItem.setDescription(descriptionMatcher.group(1));
//                            Log.e("MY", newsItem.getDescription());
//                        }
//
//
//                        items.add(newsItem);
//                    }
//
//                    isItem = false;
//                    newsItem = new NewsItem();
//                }
//            }
//        } finally {
//            inputStream.close();
//        }
//        return items;
//    }
//}
