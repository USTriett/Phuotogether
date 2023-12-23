package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddtripFragment;

import java.util.List;

public class TripListFragment extends Fragment {
    public static final int TAB_POSITION = 1;
    private ImageButton btnAddTrip;
    private TripAdapter tripAdapter;
    private RecyclerView tripRecyclerView;
    private TripListManager tripListManager;

    public static Fragment newInstance() {
        return new TripListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_triplist, container, false);

        tripListManager = TripListManager.getInstance();

        setAndGetAllView(rootView);
        setTripList();
        setEventClickButtonAddTrip();

        return rootView;
    }

    private void setEventClickButtonAddTrip() {
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtripFragment addTripFragment = new AddtripFragment();

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
    }

    private void setTripList() {
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        List<Trip> tripList = tripListManager.getTripList();
        tripAdapter = new TripAdapter(requireContext(), tripList);
        tripRecyclerView.setAdapter(tripAdapter);
    }
}
