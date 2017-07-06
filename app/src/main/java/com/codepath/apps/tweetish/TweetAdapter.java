package com.codepath.apps.tweetish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.tweetish.models.Tweet;

import java.util.List;

/**
 * Created by colemanmav on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;


    public interface TweetAdapterListener{
        public void onItemSelected(View view, int position);
    }


    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener){
        mTweets = tweets;
        mListener = listener;
        //context = timelineActivity;//TODO: ask about this context stuff
    }

    public void clear(){
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> list){
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();//TODO: ask about this context stuff

        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet,parent,false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        holder.tvRTimestamp.setText(tweet.getRelativeTimeAgo(tweet.createdAt));//TODO:clean this up by making a single method call
        Glide.with(context)
             .load(tweet.user.profileImageUrl)
             .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvRTimestamp;
        public ImageButton ibRetweet;

        public ViewHolder(View itemView){
            super(itemView);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvRTimestamp = (TextView) itemView.findViewById(R.id.tvRTimeStamp);
            ibRetweet = (ImageButton) itemView.findViewById(R.id.ibRetweet);
            ibRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReply(v);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        mListener.onItemSelected(v, position);
                    }
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// TODO: ask about restyling the way that I do this(Same issue here as in OnRetweet. I may have to apply listener pattern on both)
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("screen_name",tvScreenName.getText().toString());
                    context.startActivity(i);
                }
            });
        }

        public void onReply(View v){//TODO: ask about restyling the way that I implement this method (ouch I may have to completly change how this is happening because of the reuse of these in profile view)
            Intent i = new Intent(context,ComposeActivity.class);

            i.putExtra("replyUsername",tvScreenName.getText().toString());

            AppCompatActivity activity = (AppCompatActivity) context;

            activity.startActivityForResult(i,TimelineActivity.REQUEST_CODE);
        }
    }
}
