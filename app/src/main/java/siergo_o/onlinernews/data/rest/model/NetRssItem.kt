package siergo_o.onlinernews.data.rest.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class NetRssItem {
    @field:Element(name = "title")
    private lateinit var title: String

    @field:Element(name = "link")
    lateinit var link: String

    @field:Element(name = "pubDate")
    private lateinit var pubDate: String

    @field:Element(name = "category")
    lateinit var category: String

    @field:Element(name = "description")
    lateinit var description: String

    override fun toString(): String = ("RssItem [title=" + title + ", link=" + link + ", pubDate=" + pubDate + ", category=" + category
                + ", description=" + description + "]")

    fun getTitle(): String = title.replace("&nbsp;".toRegex(), " ")

    fun getPubDate(): String = pubDate.replace("[+]0300".toRegex(), "")
}