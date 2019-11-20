package siergo_o.onlinernews.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class RssNewsChannel
{
    @Element (name = "link")
    private String link;

    @ElementList(inline = true, required = false)
    public List<RssItem> item;

    @Override
    public String toString() {
        return "Channel [link=" + link + ", item=" + item + "]";
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<RssItem> getItem() {
        return item;
    }

    public void setItem(List<RssItem> item) {
        this.item = item;
    }
}