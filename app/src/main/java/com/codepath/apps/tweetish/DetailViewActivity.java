package com.codepath.apps.tweetish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.tweetish.models.Tweet;

import org.parceler.Parcels;

public class DetailViewActivity extends AppCompatActivity {
    Tweet tweet;
    ImageView ivProfileImage;
    TextView tvUsername, tvScreenName, tvBody;
    ImageButton ibFavorite, ibRetweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        ibFavorite = (ImageButton) findViewById(R.id.ibFavorite);
        ibRetweet = (ImageButton) findViewById(R.id.ibRetweet);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvScreenName.setText("@" + tweet.user.screenName);
        //tvRTimestamp.setText(tweet.getRelativeTimeAgo(tweet.createdAt));//TODO: make time posted appear in layout
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(ivProfileImage);

    }
}
