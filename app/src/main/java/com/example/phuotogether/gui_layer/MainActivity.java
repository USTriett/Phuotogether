package com.example.phuotogether.gui_layer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.phuotogether.R;
import com.example.phuotogether.gui_layer.info.InfoFragment;
import com.example.phuotogether.gui_layer.manual.ManualFragment;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.example.phuotogether.gui_layer.navigation.FragmentUpdateCallback;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.tripList.TripListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements FragmentUpdateCallback {

    ViewPager mViewPager;
    BottomNavigationView mBottomNavigationView;

    //region Fields
    MainFragmentPagerAdapter mPagerAdapter;
    private int mCurrentTabPosition;
    //endregion

    //region Activity lifecycle
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mPagerAdapter.removeFragment(mPagerAdapter.getItem(mCurrentTabPosition), mCurrentTabPosition)) {
            finish();
        }
    }
    //endregion

    //region FragmentUpdateCallback implementation
    @Override
    public void addFragment(Fragment fragment, int tabPosition) {
        mPagerAdapter.updateFragment(fragment, tabPosition);
    }
    //endregion
}