package siergo_o.onlinernews.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import siergo_o.onlinernews.R;
import siergo_o.onlinernews.adapter.PostsAdapter;
import siergo_o.onlinernews.model.RssFeed;
import siergo_o.onlinernews.model.RssItem;
import siergo_o.onlinernews.model.RssService;

public class FragmentNewsHolder extends ListFragment {

    RecyclerView recyclerView;
    List<RssItem> posts;

    private Fragment fragmentNews;
    private String urlLink;

    public String getUrlLink() {
        return urlLink;
    }

    public FragmentNewsHolder() {
    }

    public FragmentNewsHolder(String urlLink) {
        this.urlLink = urlLink;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        View itemView = inflater.inflate(R.layout.news_list_item_layout, container, false);

        recyclerView = rootView.findViewById(R.id.rv_news);
        CardView cvPeople = itemView.findViewById(R.id.cardview);
//        swipeNews = rootView.findViewById(R.id.swipe_news);

        recyclerView.setHasFixedSize(true);
        PostsAdapter adapter = new PostsAdapter(new ArrayList<RssItem>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        RssService.getInstance().getOnlinerAPI().getFeed().enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if (response.isSuccessful()) {
                    Log.e("123", "ssd");
                } else {
                    Log.e("321", "????????????");
                }
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                if (call.isCanceled()) {
                    System.out.println("Call was cancelled forcefully");
                } else {
                    System.out.println("Network Error :: " + t.getLocalizedMessage());
                }
            }
        });




        cvPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentNews = new FragmentNewsHolder();
                FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                transaction.replace(R.id.viewpager, fragmentNews);
                transaction.commit();
            }
        });

        return rootView;
    }




}
