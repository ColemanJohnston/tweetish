package fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweetish.R;

/**
 * Created by colemanmav on 7/3/17.
 */

public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    private String tabTitles[] = new String[]{"Home","Mentions"};
    private int tabIcons[] = {R.drawable.ic_home_twitter,R.drawable.ic_mentions_twitter};
    Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    //return title
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }



    //return the total number of fragments
    @Override
    public int getCount() {
        return 2;
    }

    //return the fragment to use depending on the position
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new HomeTimelineFragment();
        }
        if(position == 1){
            return new MentionsTimelineFragment();
        }
        return null;
    }

    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }
}
