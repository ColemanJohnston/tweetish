package com.codepath.apps.tweetish;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");

        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);

        ft.commit();

    }
}
