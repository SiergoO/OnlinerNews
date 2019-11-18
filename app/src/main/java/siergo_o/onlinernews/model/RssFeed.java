package siergo_o.onlinernews.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root (name = "rss", strict = false)
public class RssFeed {

    @Element(name = "channel")
    public RssChannel channel;


    @Override
    public String toString() {
        return "RssFeed [channel=" + channel + "]";
    }

    public RssChannel getChannel() {
        return channel;
    }

    public void setChannel(RssChannel channel) {
        this.channel = channel;
    }
}

