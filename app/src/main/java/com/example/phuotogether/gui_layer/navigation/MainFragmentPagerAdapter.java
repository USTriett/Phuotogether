package com.example.phuotogether.gui_layer.navigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.phuotogether.gui_layer.info.InfoFragment;
import com.example.phuotogether.gui_layer.manual.ManualFragment;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.example.phuotogether.gui_layer.trip.tripList.TripListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *
 * Pager adapter that keeps state of the fragments inside the bottom page navigation tabs
 *
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    //region Statics
    private static final List<Fragment> BASE_FRAGMENTS = Arrays.asList(MapFragment.newInstance(), TripListFragment.newInstance(), ManualFragment.newInstance(), InfoFragment.newInstance());
    private static final int MAP_POSITION = 0;
    private static final int TRIP_POSITION = 1;
    private static final int MANUAL_POSITION = 2;
    private static final int INFO_POSITION = 3;
    //endregion
    //region Fields
    private List<Fragment> mMapFragments;
    private List<Fragment> mTripFragments;
    private List<Fragment> mManualFragments;
    private List<Fragment> mInfoFragments;

    //endregion
    //region constructor
    public MainFragmentPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
        mMapFragments = new ArrayList<>();
        mTripFragments = new ArrayList<>();
        mManualFragments = new ArrayList<>();
        mInfoFragments = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return BASE_FRAGMENTS.size();
    }


    @Override
    public long getItemId(int position) {
        if (position == MAP_POSITION
                && getItem(position).equals(BASE_FRAGMENTS.get(position))) {
            return MAP_POSITION;
        } else if (position == TRIP_POSITION
                && getItem(position).equals(BASE_FRAGMENTS.get(position))) {
            return TRIP_POSITION;
        } else if (position == MANUAL_POSITION
                && getItem(position).equals(BASE_FRAGMENTS.get(position))) {
            return MANUAL_POSITION;
        } else if (position == INFO_POSITION
                && getItem(position).equals(BASE_FRAGMENTS.get(position))) {
            return INFO_POSITION;
        }
        return getItem(position).hashCode();
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        if (position == MAP_POSITION) {
            if (mMapFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mMapFragments.get(mMapFragments.size() - 1);
        } else if (position == TRIP_POSITION) {
            if (mTripFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mTripFragments.get(mTripFragments.size() - 1);
        } else if (position == MANUAL_POSITION) {
            if (mManualFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mManualFragments.get(mManualFragments.size() - 1);
        } else {
            if (mInfoFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mInfoFragments.get(mInfoFragments.size() - 1);
        }
    }

    public void updateFragment(Fragment fragment, int position) {
        if (!BASE_FRAGMENTS.contains(fragment)) {
            addInnerFragment(fragment, position);
        }
        notifyDataSetChanged();
    }
    private void addInnerFragment(Fragment fragment, int position) {
        if (position == MAP_POSITION) {
            mMapFragments.add(fragment);
        } else if (position == TRIP_POSITION) {
            mTripFragments.add(fragment);
        } else if (position == MANUAL_POSITION) {
            mManualFragments.add(fragment);
        } else {
            mInfoFragments.add(fragment);
        }
    }
    public boolean removeFragment(Fragment fragment, int position) {
        if (position == MAP_POSITION) {
            if (mMapFragments.contains(fragment)) {
                removeInnerFragment(fragment, mMapFragments);
                return true;
            }
        } else if (position == TRIP_POSITION) {
            if (mTripFragments.contains(fragment)) {
                removeInnerFragment(fragment, mTripFragments);
                return true;
            }
        } else if (position == MANUAL_POSITION) {
            if (mManualFragments.contains(fragment)) {
                removeInnerFragment(fragment, mManualFragments);
                return true;
            }
        } else if (position == INFO_POSITION) {
            if (mInfoFragments.contains(fragment)) {
                removeInnerFragment(fragment, mInfoFragments);
                return true;
            }
        }
        return false;
    }
    private void removeInnerFragment(Fragment fragment, @NonNull List<Fragment> tabFragments) {
        tabFragments.remove(fragment);
        notifyDataSetChanged();
    }
}