package com.zanrite.groomme.HistoryData;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class Tabs_past_future_ride extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private double My_lat=0,My_long=0;
    private Bundle bundle;
    private int Pager=0;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_later_tab);
        pref = new PrefManager(getApplicationContext());
        toolbar = findViewById(R.id.toolbar_later_tabs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = findViewById(R.id.viewpager_tabs);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager,bundle);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getResposibility()==1) {
                    Intent i = new Intent(Tabs_past_future_ride.this, UserHomeScreen.class);
                    i.putExtra("my_lat", My_lat);
                    i.putExtra("my_long", My_long);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else if(pref.getResposibility()==2) {
                    Intent i = new Intent(Tabs_past_future_ride.this, SalonHomePage.class);
                    i.putExtra("my_lat", My_lat);
                    i.putExtra("my_long", My_long);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });



    }

    private void setupViewPager(ViewPager viewPager, Bundle bundle) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Present_fragment fragObj2 = new Present_fragment();
        fragObj2.setArguments(bundle);
        adapter.addFragment(fragObj2,"Accepted");
        Future_fragment fragObj1 = new Future_fragment();
        fragObj1.setArguments(bundle);
        adapter.addFragment(fragObj1,"Requested");
        PastFragment fragobj = new PastFragment();
        fragobj.setArguments(bundle);
        adapter.addFragment(fragobj, "History");
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            if(pref.getResposibility()==1) {
                Intent i = new Intent(Tabs_past_future_ride.this, UserHomeScreen.class);
                i.putExtra("my_lat", My_lat);
                i.putExtra("my_long", My_long);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else if(pref.getResposibility()==2) {
                Intent i = new Intent(Tabs_past_future_ride.this, SalonHomePage.class);
                i.putExtra("my_lat", My_lat);
                i.putExtra("my_long", My_long);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_black, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
