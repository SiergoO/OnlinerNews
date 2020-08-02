package siergo_o.onlinernews.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class RssItem {
    @Element(name = "title")
    private var title: String? = null

    @Element(name = "link")
    var link: String? = null

    @Element(name = "pubDate")
    private var pubDate: String? = null

    @Element(name = "category")
    var category: String? = null

    @Element(name = "description")
    var description: String? = null

    override fun toString(): String = ("RssItem [title=" + title + ", link=" + link + ", pubDate=" + pubDate + ", category=" + category
                + ", description=" + description + "]")

    fun getTitle(): String = title!!.replace("&nbsp;".toRegex(), " ")

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPubDate(): String = pubDate!!.replace("[+]0300".toRegex(), "")

    fun setPubDate(pubDate: String?) {
        this.pubDate = pubDate
    }
}