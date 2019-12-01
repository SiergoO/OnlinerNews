package siergo_o.onlinernews.model;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssService {

    private Retrofit mRetrofit;
    private String urlLink;

    public RssService(String urlLink){
        mRetrofit = new Retrofit.Builder().baseUrl(urlLink)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();
    }

//    public RssService(){
//        mRetrofit = new Retrofit.Builder().baseUrl(urlLink)
//                .addConverterFactory(SimpleXmlConverterFactory.create()).build();
//    }

    public static RssService getInstance(String urlLink){

        return new RssService(urlLink);
    }

    public OnlinerAPI getOnlinerAPI(){
        return mRetrofit.create(OnlinerAPI.class);
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {

        this.urlLink = urlLink;
    }

}
