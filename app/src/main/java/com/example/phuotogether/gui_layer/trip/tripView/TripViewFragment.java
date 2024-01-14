package com.example.phuotogether.gui_layer.trip.tripView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.manual.ManualItemFragment;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.navigation.TripViewPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddtripFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TripViewFragment extends Fragment {
    public static final int TAB_POSITION = 1;
    private ImageButton btnBack;
    private TextView tvTitle;
    private TripListManager tripListManager;
    private Trip selectedTrip;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.luggage_icon,
            R.drawable.schedule_icon,
            R.drawable.diary_icon
    };

    public static TripViewFragment newInstance() {
        TripViewFragment fragment = new TripViewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_view_trip, container, false);
        tripListManager = TripListManager.getInstance();
        setAndGetAllView(rootView);
        setSelectedTrip();
        setTitleTripView();
        setEventClickButtonBack();

        viewPager = rootView.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return rootView;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.setTabIconTint(null);
    }

    private void setupViewPager(ViewPager viewPager) {
        TripViewPagerAdapter adapter = new TripViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TripLuggageFragment(), "Hành lý");
        adapter.addFragment(new TripScheduleFragment().newInstance(selectedTrip), "Lịch trình");

        viewPager.setAdapter(adapter);
    }

    private void setSelectedTrip() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            int tripPosition = bundle.getInt("trip_position", -1);

            if (tripPosition != -1) {
                selectedTrip = tripListManager.getTripAtPosition(tripPosition);
            }
        }
    }

    private void setTitleTripView() {
        String titleTripView = selectedTrip.getTripName();
        tvTitle.setText("Hành trình / " + titleTripView);
    }

    private void setEventClickButtonBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setAndGetAllView(View rootView) {
        btnBack = rootView.findViewById(R.id.buttonBackViewTrip);
        tvTitle = rootView.findViewById(R.id.tvTitleViewTrip);
    }

}