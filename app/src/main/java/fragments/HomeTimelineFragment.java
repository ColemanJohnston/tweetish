package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.tweetish.TwitterApp;
import com.codepath.apps.tweetish.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by colemanmav on 7/3/17.
 */

public class HomeTimelineFragment extends TweetsListFragment {


    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();

        populateTimeline();
    }


    private void populateTimeline(){
//        showProgressBar();
        ((NetworkCallListener) getActivity()).onNetworkCallStart();
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                log.d("TwitterClient", response.toString());
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                hideProgressBar();
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", response.toString());
                //tweetAdapter.clear(); //TODO: figure out how to do this stuff from other method.
                addItems(response);
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                hideProgressBar();
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", responseString);
                throwable.printStackTrace();
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                hideProgressBar();
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                hideProgressBar();
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
//                swipeContainer.setRefreshing(false);
            }
        });
    }
}
