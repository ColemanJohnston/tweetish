package com.codepath.apps.tweetish.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by colemanmav on 6/26/17.
 */

public class Tweet {
    public String body;
    public long uid;
    public String createdAt;

    public User user;

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract values from json
        tweet.body = jsonObject.getString("text");

        tweet.uid = jsonObject.getLong("id");

        tweet.createdAt = jsonObject.getString("created_at");

        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        return tweet;
    }

}
