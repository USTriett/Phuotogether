package com.example.phuotogether.gui_layer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.phuotogether.R;
import com.example.phuotogether.gui_layer.info.InfoFragment;
import com.example.phuotogether.gui_layer.manual.ManualFragment;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.tripList.TripListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    BottomNavigationView mBottomNavigationView;

    //region Fields
    MainFragmentPagerAdapter mPagerAdapter;
    private int mCurrentTabPosition;
    //endregion

    public MainFragmentPagerAdapter getPagerAdapter(){
        return mPagerAdapter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.navigation_map) {
                mCurrentTabPosition = MapFragment.TAB_POSITION;
                mViewPager.setCurrentItem(mCurrentTabPosition);
                return true;
            } else if (itemId == R.id.navigation_trip) {
                mCurrentTabPosition = TripListFragment.TAB_POSITION;
                mViewPager.setCurrentItem(mCurrentTabPosition);
                return true;
            } else if (itemId == R.id.navigation_manual) {
                mCurrentTabPosition = ManualFragment.TAB_POSITION;
                mViewPager.setCurrentItem(mCurrentTabPosition);
                return true;
            } else if (itemId == R.id.navigation_info) {
                mCurrentTabPosition = InfoFragment.TAB_POSITION;
                mViewPager.setCurrentItem(mCurrentTabPosition);
                return true;
            } else {
                return false;
            }
        });

        // Add a ViewPager listener to update BottomNavigationView when swiping
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this use case
            }

            @Override
            public void onPageSelected(int position) {
                // Update BottomNavigationView when the page changes
                switch (position) {
                    case MapFragment.TAB_POSITION:
                        mBottomNavigationView.setSelectedItemId(R.id.navigation_map);
                        break;
                    case TripListFragment.TAB_POSITION:
                        mBottomNavigationView.setSelectedItemId(R.id.navigation_trip);
                        break;
                    case ManualFragment.TAB_POSITION:
                        mBottomNavigationView.setSelectedItemId(R.id.navigation_manual);
                        break;
                    case InfoFragment.TAB_POSITION:
                        mBottomNavigationView.setSelectedItemId(R.id.navigation_info);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed for this use case
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mPagerAdapter.removeFragment(mPagerAdapter.getItem(mCurrentTabPosition), mCurrentTabPosition)) {
            finish();
        }
    }


    //endregion

}