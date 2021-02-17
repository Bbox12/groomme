package com.zanrite.groomme.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zanrite.groomme.Fragments.AboutFragment;
import com.zanrite.groomme.Fragments.GalleryFragment;
import com.zanrite.groomme.Fragments.ReviewsFragment;
import com.zanrite.groomme.Fragments.ServicesFragment;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ServicesFragment();
            case 1:
            return new AboutFragment();
            case 2:
                return new GalleryFragment();
            case 3:
                return new ReviewsFragment();
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}