package com.codepath.apps.tweetish;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.tweetish.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fragments.TweetsListFragment;
import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity implements TweetsListFragment.NetworkCallListener{

    TwitterClient client;
    MenuItem miActionProgressItem;
    private int networkCallCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        networkCallCount = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose,menu);//TODO rename menu_compose to be more general
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //set up options menu
        miActionProgressItem = menu.findItem(R.id.miActionProgress);

        //everything that needs to happen after options menu is set up
        String screenName = getIntent().getStringExtra("screen_name");

        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);

        ft.commit();

        client = TwitterApp.getRestClient();
        onNetworkCallStart();
        if(screenName == null){
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    onNetworkCallFinish();
                    super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle("@" + user.screenName);
                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", errorResponse.toString());
                }
            });
        }
        else {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    onNetworkCallFinish();
                    super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle("@" + user.screenName);
                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    onNetworkCallFinish();
                    Log.e("ProfileActivity", errorResponse.toString());
                }
            });
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar(){
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar(){
        miActionProgressItem.setVisible(false);
    }

    public void populateUserHeadline(User user){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagline);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }

    @Override
    public void onNetworkCallStart() {
        networkCallCount++;
        showProgressBar();
    }

    @Override
    public void onNetworkCallFinish() {
        networkCallCount--;
        if(networkCallCount < 1){
            hideProgressBar();
        }
    }
}
