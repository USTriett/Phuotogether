package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.TripDestinationsManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.databinding.FragmentTripScheduleBinding;
import com.example.phuotogether.dto.PlannedDestination;
import com.example.phuotogether.gui_layer.trip.destinationList.DestinationListAdapter;
import com.example.phuotogether.gui_layer.trip.destinationList.SearchDestinationActivity;

import java.util.List;

public class TripScheduleFragment extends Fragment {

    private FragmentTripScheduleBinding binding;

    private static Trip selectedTrip;
    private TripDestinationsManager tripDestinationsManager;

    public TripScheduleFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static TripScheduleFragment newInstance(Trip trip) {
        TripScheduleFragment fragment = new TripScheduleFragment();
        selectedTrip = trip;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentTripScheduleBinding.inflate(inflater, container, false);
        binding.rvDestinationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripDestinationsManager = TripDestinationsManager.getInstance();

        List<PlannedDestination> plannedDestinationList = tripDestinationsManager.getDestinationList();
        Log.e("TripScheduleFragment", "onCreateView: " + plannedDestinationList.size());
        DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(),plannedDestinationList);
        binding.rvDestinationList.setAdapter(destinationListAdapter);


        binding.edtSearchPlace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getActivity(), SearchDestinationActivity.class);
                intent.putExtra("trip", selectedTrip);
                startActivity(intent);
                return false;
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        Log.e("TripScheduleFragment", "onResume: ");
        super.onResume();
        List<PlannedDestination> plannedDestinationList = tripDestinationsManager.getDestinationList();
        if (plannedDestinationList.size() >=0 ) {
            TextView numberDestination = binding.noDestination;
            numberDestination.setText("Bạn có " + plannedDestinationList.size() + " điểm đến trong lịch trình");
            DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(), plannedDestinationList);
            binding.rvDestinationList.setAdapter(destinationListAdapter);
        }

    }

}