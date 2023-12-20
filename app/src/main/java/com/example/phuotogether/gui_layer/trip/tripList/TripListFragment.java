package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.gui_layer.manual.ManualFragment;

import java.util.ArrayList;
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

        tripListManager = new TripListManager();
        Log.d("fkfk31", "");

        setAndGetAllView(rootView);
        Log.d("fkfk32", "");
        setTripList();
        Log.d("fkfk33", "");
        setEventClickButtonAddTrip();
        Log.d("fkfk34", "");

        return rootView;
    }

    private void setEventClickButtonAddTrip() {
//        btnAddTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(requireActivity(), AddTripActivity.class));
//            }
//        });
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
