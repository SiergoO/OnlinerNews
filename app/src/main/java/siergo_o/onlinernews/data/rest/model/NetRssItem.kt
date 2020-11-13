package siergo_o.onlinernews.data.rest.model

import com.google.gson.annotations.SerializedName

data class NetRssItem(
        @SerializedName("title") var title: String,
        @SerializedName("description") var description: String,
        @SerializedName("link") var link: String,
        @SerializedName("created") var pubDate: String
)
