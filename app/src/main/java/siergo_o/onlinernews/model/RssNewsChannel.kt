package siergo_o.onlinernews.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)

class RssNewsChannel {
    @Element(name = "link")
    var link: String? = null

    @ElementList(inline = true, required = false)
    var item: List<RssItem>? = null

    override fun toString(): String {
        return "Channel [link=$link, item=$item]"
    }
}