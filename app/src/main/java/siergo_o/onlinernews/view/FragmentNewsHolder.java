package siergo_o.onlinernews.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView recyclerView;
    private List<RssItem> posts;
    private Context context;
    private Fragment fragmentNews;
    private String urlLink;
    private RecyclerView.Adapter adapter;
    private int position;


    public FragmentNewsHolder(String urlLink, int position) {
        this.urlLink = urlLink;
        this.position = position;

    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public String getUrlLink() {
        switch (position){
            case 0: return "https://tech.onliner.by/";
            case 1: return "https://people.onliner.by/";
            case 2: return "https://auto.onliner.by/";
        }
        return urlLink;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {



        final View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        final View itemView = inflater.inflate(R.layout.news_list_item_layout, container, false);


        CardView cvPeople = itemView.findViewById(R.id.cardview);
        recyclerView = rootView.findViewById(R.id.rv_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        adapter = null;
        recyclerView.setAdapter(adapter);

        Call<RssFeed> call = RssService.getInstance(this.getUrlLink()).getOnlinerAPI().getFeed();

        call.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {

                posts = response.body().getChannel().getItem();
                PostsAdapter adapter = new PostsAdapter(posts, getActivity());

                for (int i = 0; i < posts.size(); i++) {
                    Log.e("J", posts.get(i).toString());
//                    Log.e("THIS", posts.get(i).toString());
                }


                if (response.isSuccessful()) {
                    Log.e("123", response.body().getChannel().getItem().get(0).toString());

                } else {
                    Log.e("321", "????????????");
                }

                recyclerView.setAdapter(adapter);
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
                fragmentNews = new FragmentNewsHolder(urlLink, position);
                FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                transaction.replace(R.id.viewpager, fragmentNews);
                transaction.commit();
            }
        });

        return rootView;
    }


}
