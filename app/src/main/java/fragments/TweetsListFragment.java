package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweetish.R;
import com.codepath.apps.tweetish.TweetAdapter;
import com.codepath.apps.tweetish.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by colemanmav on 7/3/17.
 */

public abstract class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener{

    public interface TweetSelectedListener{
        void onTweetSelected(Tweet tweet);
    }

    public interface NetworkCallListener{
        void onNetworkCallFinish();
        void onNetworkCallStart();
    }

    // Fields
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    SwipeRefreshLayout swipeContainer;

    // inflation happens inside OnCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragments_tweets_list, container, false);

        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets, this);
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTweets.setAdapter(tweetAdapter);

        this.swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.srlTweetsList);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refresh();
                swipeContainer.setRefreshing(false);
            }
        });

        return v;
    }

    public abstract void refresh();

    public void appendTweet(Tweet tweet){
        tweets.add(0,tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);
    }

    public void addItems(JSONArray response){
        tweetAdapter.clear();
        try{
            for(int i = 0; i < response.length(); ++i){
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                //tweetAdapter.notifyItemInserted(tweets.size() - 1);
            }
            tweetAdapter.notifyDataSetChanged(); //switched
            rvTweets.scrollToPosition(0);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }

}
