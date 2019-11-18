package siergo_o.onlinernews;

/**
 * Created by AbbasHassan on 4/14/2017.
 */

public class NewsItem {

    private String title;
    private String date;
    private String description;
    private String url;
    private String imgUrl;


    public NewsItem(String title, String date, String description, String url, String imgUrl) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public NewsItem() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
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

