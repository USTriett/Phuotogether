package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.FragmentUpdateListener;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddtripFragment;
import com.example.phuotogether.gui_layer.trip.tripView.TripLuggageFragment;

import java.util.ArrayList;
import java.util.List;

public class TripListFragment extends Fragment implements FragmentUpdateListener {
    public static final int TAB_POSITION = 1;
    private ImageButton btnAddTrip;
    private TripAdapter tripAdapter;
    private RecyclerView tripRecyclerView;
    private TripListManager tripListManager;
    private User user = new User();
    private MainActivity mainActivity;
    private List<Trip> finalTripList = new ArrayList<>();


    public static Fragment newInstance() {
        return new TripListFragment();
    }
    @Override
    public void onUpdate(User user) {
        this.user = user;
        setTripList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_triplist, container, false);

        setAndGetAllView(rootView);
        setEventClickButtonAddTrip();

        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setTripList();
                    }});
        return rootView;
    }

    private void setEventClickButtonAddTrip() {
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtripFragment addTripFragment = new AddtripFragment();
//                TripLuggageFragment tripLuggageFragment = new TripLuggageFragment();

                MainFragmentPagerAdapter pagerAdapter = ((MainActivity)requireContext()).getPagerAdapter();
                pagerAdapter.updateFragment(addTripFragment, TAB_POSITION);

                FragmentTransaction transaction = ((FragmentActivity) requireContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.triplist, addTripFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void setAndGetAllView(View view) {
        btnAddTrip = view.findViewById(R.id.btnAddTrip);
        tripRecyclerView = view.findViewById(R.id.recycleViewListTrip);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        tripAdapter = new TripAdapter(getActivity(), finalTripList);
        tripRecyclerView.setAdapter(tripAdapter);
    }

    private void setTripList() {
        tripListManager = TripListManager.getInstance(user);

        tripListManager.getTripList(new TripListManager.FetchTripListCallback() {
            @Override
            public void onFetchTripResult(boolean success, List<Trip> tripList) {
                if (success) {
                    Log.d("TripListDatabase", "onFetchTripResult: " + tripList.size());
                    tripAdapter.setTripList(tripList);
                }
            }
        });

    }
}
