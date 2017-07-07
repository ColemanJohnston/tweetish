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

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();

        populateTimeline();
    }

    @Override
    public void refresh() {
        populateTimeline();
    }

    private void populateTimeline(){
        ((NetworkCallListener) getActivity()).onNetworkCallStart();
        //TODO: put show progress bar here
        client.getMentionsTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", response.toString());
                addItems(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                ((NetworkCallListener) getActivity()).onNetworkCallFinish();
                log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
