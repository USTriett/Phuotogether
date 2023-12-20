package com.example.phuotogether.gui_layer.trip.addTrip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phuotogether.R;

public class AddTripFragment extends Fragment {

    int mPosition;
    public AddTripFragment() {

    }

    public AddTripFragment(int position){
        mPosition = position;
    }

    public static AddTripFragment newInstance(int position) {
        return new AddTripFragment(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_trip, container, false);
    }
}