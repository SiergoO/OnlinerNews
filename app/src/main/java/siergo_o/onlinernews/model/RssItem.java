package siergo_o.onlinernews.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class RssItem {
    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "pubDate")
    private String pubDate;

    @Element(name = "category")
    private String category;

    @Element(name = "description")
    private String description;

//    @XmlAnyElement(name = "media:thumbnail")
//    private MediaThumbnail mediaThumbnail;
//
//    public class MediaThumbnail {
//
//        @Attribute(name = "url")
//        private String imageUrl;
//    }

    @Override
    public String toString() {
        return "RssItem [title=" + title + ", link=" + link + ", pubDate=" + pubDate + ", category=" + category
                + ", description=" + description + "]";
    }

    public String getTitle() {
        title = title.replaceAll("&nbsp;", " ");
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        pubDate = pubDate.replaceAll("[+]0300", "");
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public MediaThumbnail getMediaThumbnail() {
//        return mediaThumbnail;
//    }
//
//    public void setMediaThumbnail(MediaThumbnail mediaThumbnail) {
//        this.mediaThumbnail = mediaThumbnail;
//    }
}
