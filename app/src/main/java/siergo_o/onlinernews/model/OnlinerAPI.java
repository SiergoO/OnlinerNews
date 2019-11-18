package siergo_o.onlinernews.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OnlinerAPI {
    @GET("/feed")
    Call<RssFeed> getFeed();
}
