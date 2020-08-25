package siergo_o.onlinernews.data.rest.model;

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)

class NetRssFeed {
    @field:Element(name = "channel")
    var channel: NetRssNewsChannel? = null

    override fun toString(): String {
        return "RssFeed [channel=$channel]"
    }
}
