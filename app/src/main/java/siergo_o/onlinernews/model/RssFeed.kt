package siergo_o.onlinernews.model;

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)

class RssFeed {
    @field:Element(name = "channel")
    var channel: RssNewsChannel? = null

    override fun toString(): String {
        return "RssFeed [channel=$channel]"
    }
}

