package siergo_o.onlinernews.data.rest.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class NetRssItem {
    @field:Element(name = "title")
    lateinit var title: String

    @field:Element(name = "link")
    lateinit var link: String

    @field:Element(name = "pubDate")
    lateinit var pubDate: String

    @field:Element(name = "description")
    lateinit var description: String

    @field:Element(name = "thumbnail", required = false)
    lateinit var thumbnail: Thumbnail

    @Root(name = "thumbnail", strict = false)
    class Thumbnail {
        @field:Attribute(name = "url")
       lateinit var url: String
    }
}