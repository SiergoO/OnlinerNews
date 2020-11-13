package siergo_o.onlinernews.data.rest.model

import com.google.gson.annotations.SerializedName

data class NetRssChannel (
    @SerializedName("items")var items: List<NetRssItem>
)