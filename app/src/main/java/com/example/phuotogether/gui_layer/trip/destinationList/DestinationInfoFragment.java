package com.example.phuotogether.gui_layer.trip.destinationList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phuotogether.R;
import com.example.phuotogether.databinding.FragmentDestinationInfoBinding;
import com.example.phuotogether.dto.PlannedDestination;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestinationInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationInfoFragment extends Fragment {

    private FragmentDestinationInfoBinding binding;
    private static PlannedDestination selectedDestination;


    public DestinationInfoFragment() {
        // Required empty public constructor
    }

    public static DestinationInfoFragment newInstance(PlannedDestination destination) {
        DestinationInfoFragment fragment = new DestinationInfoFragment();
        selectedDestination = destination;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDestinationInfoBinding.inflate(inflater, container, false);
        binding.tvName.setText(selectedDestination.getLocationName());
        binding.tvAddress.setText(selectedDestination.getLocationAddress());
        binding.tvDateTime.setText(selectedDestination.getBeginTime() + " - " + selectedDestination.getEndTime());


        return binding.getRoot();
    }
}