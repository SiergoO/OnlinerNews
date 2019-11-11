package siergo_o.onlinernews;

import java.io.Serializable;

/**
 * Created by AbbasHassan on 4/14/2017.
 */

public class NewsItem {

    private String title;
    private String date;
    private String description;
    private String url;
    private String imgUrl;


    NewsItem(String title, String date, String description, String url, String imgUrl) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }
}

