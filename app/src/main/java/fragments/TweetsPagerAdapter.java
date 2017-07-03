package fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by colemanmav on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Home","Mentions"};
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
}
