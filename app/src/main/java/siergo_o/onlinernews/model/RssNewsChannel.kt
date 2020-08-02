package siergo_o.onlinernews.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)

class RssNewsChannel {
    @field:Element(name = "link")
    var link: String? = null

    @field:ElementList(name = "item", required = false, inline = true)
    var items: List<RssItem>? = null

    override fun toString(): String {
        return "Channel [link=$link, item=$items]"
    }
}