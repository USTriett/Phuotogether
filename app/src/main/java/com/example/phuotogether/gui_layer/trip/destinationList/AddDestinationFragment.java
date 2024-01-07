package com.example.phuotogether.gui_layer.trip.destinationList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.TripDestinationsManager;
import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.databinding.FragmentAddPlaceBinding;
import com.example.phuotogether.dto.PlannedDestination;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDestinationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDestinationFragment extends Fragment {

    private FragmentAddPlaceBinding binding;
    private static Trip selectedTrip;

    private static GooglePlaceModel selectedPlace;
    private TripDestinationsManager tripDestinationsManager;

    public AddDestinationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddDestinationFragment newInstance(GooglePlaceModel param1, Trip param2) {
        AddDestinationFragment fragment = new AddDestinationFragment();
        selectedTrip = param2;
        selectedPlace = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        tripDestinationsManager = TripDestinationsManager.getInstance();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //hide soft keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Inflate the layout for this fragment
        binding = FragmentAddPlaceBinding.inflate(inflater, container, false);
        binding.tvPlaceName.setText(selectedPlace.getName());
        binding.tvAddress.setText(selectedPlace.getFormattedAddress());
        binding.tvDate.setText("Ngày: " + selectedTrip.getStartDate());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beginTime = binding.etArriveTime.getText().toString();
                String endTime = binding.etLeaveTime.getText().toString();
                String note = binding.etNote.getText().toString();
                PlannedDestination plannedDestination = new
                        PlannedDestination(1, 1,
                        1,  beginTime,endTime, note);
                plannedDestination.setLocationName(selectedPlace.getName());
                plannedDestination.setLocationAddress(selectedPlace.getFormattedAddress());
                tripDestinationsManager.addDestination(plannedDestination);

                requireActivity().finish();
            }
        });
    }
}