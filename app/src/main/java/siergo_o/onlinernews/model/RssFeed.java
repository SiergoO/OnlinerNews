package siergo_o.onlinernews.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root (name = "rss", strict = false)
public class RssFeed {

    @Element(name = "channel")
    public RssNewsChannel channel;


    @Override
    public String toString() {
        return "RssFeed [channel=" + channel + "]";
    }

    public RssNewsChannel getChannel() {
        return channel;
    }

    public void setChannel(RssNewsChannel channel) {
        this.channel = channel;
    }
}

