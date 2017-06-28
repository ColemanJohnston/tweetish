package com.codepath.apps.tweetish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.tweetish.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private EditText etTweet;
    private Button btnSend;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        context = this;
        etTweet = (EditText) findViewById(R.id.etTweet);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:send tweet and go back to timeline
                //TODO: think about checking if the user is trying to send an empty tweet.
                TwitterClient client = new TwitterClient(context);

                client.sendTweet(etTweet.getText().toString(), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //turn response into a tweet object
                        Toast.makeText(context,"Tweet was sent",Toast.LENGTH_SHORT).show();
                        try{
                            Tweet tweet = Tweet.fromJSON(response);
                            Intent data = new Intent();
                            // Pass relevant data back as a result
                            data.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                            // Activity finished ok, return the data
                            setResult(RESULT_OK, data); // set result code and bundle data for response
                            finish(); // closes the activity, pass data to parent
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("ComposeActivity",responseString);
                        throwable.printStackTrace();
                        Toast.makeText(context, "Tweet failed to send",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("ComposeActivity",errorResponse.toString());
                        throwable.printStackTrace();
                        Toast.makeText(context, "Tweet failed to send",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("ComposeActivity",errorResponse.toString());
                        throwable.printStackTrace();
                        Toast.makeText(context, "Tweet failed to send",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }




}
