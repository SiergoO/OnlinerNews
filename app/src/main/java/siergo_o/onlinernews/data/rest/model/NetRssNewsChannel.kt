package siergo_o.onlinernews.data.rest.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import siergo_o.onlinernews.data.rest.model.NetRssItem

@Root(name = "channel", strict = false)

class NetRssNewsChannel {
    @field:Element(name = "link")
    lateinit var link: String

    @field:ElementList(name = "item", required = false, inline = true)
    lateinit var items: List<NetRssItem>

    override fun toString(): String {
        return "Channel [link=$link, item=$items]"
    }
}