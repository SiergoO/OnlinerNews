package siergo_o.onlinernews.model;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssService {

    private static RssService mInstance;
    private Retrofit mRetrofit;
    private static final String BASE_URL = "https://people.onliner.by/";

    private RssService(){
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();
    }

    public static RssService getInstance(){
        if (mInstance == null) {
            mInstance = new RssService();
        }
        return mInstance;
    }

    public OnlinerAPI getOnlinerAPI(){
        return mRetrofit.create(OnlinerAPI.class);
    }


}
