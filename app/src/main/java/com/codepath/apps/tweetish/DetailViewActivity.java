package com.codepath.apps.tweetish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.tweetish.models.Tweet;

public class DetailViewActivity extends AppCompatActivity {
    Tweet tweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
    }
}
