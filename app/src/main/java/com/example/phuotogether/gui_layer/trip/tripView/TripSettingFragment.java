package com.example.phuotogether.gui_layer.trip.tripView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;

public class TripSettingFragment extends Fragment {
    private static Trip selectedTrip;

    public static TripSettingFragment newInstance(Trip trip) {
        selectedTrip = trip;
        return new TripSettingFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_setting_trip, container, false);



        return rootView;
    }
}