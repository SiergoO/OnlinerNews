package siergo_o.onlinernews.data.rest.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import siergo_o.onlinernews.data.rest.model.NetRssItem

@Root(name = "channel", strict = false)

class NetRssNewsChannel {
    @field:Element(name = "link")
    var link: String? = null

    @field:ElementList(name = "item", required = false, inline = true)
    var items: List<NetRssItem>? = null

    override fun toString(): String {
        return "Channel [link=$link, item=$items]"
    }
}