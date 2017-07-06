package com.codepath.apps.tweetish;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.apps.tweetish.models.Tweet;

import org.parceler.Parcels;

import fragments.TweetsListFragment;
import fragments.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener{

    public static int REQUEST_CODE = 10;//TODO:find if this is a good request code


    private SwipeRefreshLayout swipeContainer;
    MenuItem miActionProgressItem;

    TweetsPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //get viewpager
        ViewPager vpager = (ViewPager) findViewById(R.id.viewpager);

        //set adapter for pager

        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        vpager.setAdapter(adapterViewPager);

        // setup the tabLayout to use viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpager);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        this.swipeContainer = (SwipeRefreshLayout) findViewById(R.id.srlTimeline);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//
//            @Override
//            public void onRefresh() {
//               // populateTimeline();
//                swipeContainer.setRefreshing(false);
//            }
//        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        populateTimeline();
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar(){
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar(){
        miActionProgressItem.setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        return true;
    }

    public void onComposeAction(MenuItem mi){
        this.startActivityForResult(new Intent(this,ComposeActivity.class),REQUEST_CODE);
//        Toast.makeText(this,"compose was pressed",Toast.LENGTH_SHORT).show();
    }

    @Override //TODO: finish this method once compose activity is finished
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra(Tweet.class.getSimpleName()));
            ((TweetsListFragment) adapterViewPager.getRegisteredFragment(0)).appendTweet(tweet);//add tweet to timeline
        }
        else{
            //TODO: check if an else statement should be added here.
        }
    }

    public void onProfileView(MenuItem item){
        //launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Intent intent = new Intent(this,DetailViewActivity.class);
        intent.putExtra("tweet", Parcels.wrap(tweet));
        this.startActivity(intent);
    }
}
