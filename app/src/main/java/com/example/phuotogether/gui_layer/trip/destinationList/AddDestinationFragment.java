package com.example.phuotogether.gui_layer.trip.destinationList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.TripDestinationsManager;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.databinding.FragmentAddPlaceBinding;
import com.example.phuotogether.dto.Location;
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
    private static int destinationNo;

    public AddDestinationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddDestinationFragment newInstance(GooglePlaceModel param1, Trip param2, int desNo) {
        AddDestinationFragment fragment = new AddDestinationFragment();
        selectedTrip = param2;
        selectedPlace = param1;
        destinationNo = desNo;
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
                String beginTime = selectedTrip.getStartDate() + "T" + binding.etArriveTime.getText().toString();
                String endTime = selectedTrip.getStartDate() + "T" + binding.etLeaveTime.getText().toString();
                Log.d("AddDestinationFragment", "onClick: " + beginTime);
                String note = binding.etNote.getText().toString();
//                PlannedDestination plannedDestination = new
//                        PlannedDestination(selectedTrip.getId(), 1,
//                        destinationNo,  beginTime,endTime, note);
//                plannedDestination.setLocationName(selectedPlace.getName());
//                plannedDestination.setLocationAddress(selectedPlace.getFormattedAddress());
//                tripDestinationsManager.addDestination(plannedDestination);
//                requireActivity().getSupportFragmentManager().popBackStack();
                Location location = new Location(0,selectedPlace.getGeometry().getLocation().getLat(),
                        selectedPlace.getGeometry().getLocation().getLng(),
                        selectedPlace.getName(),
                        selectedPlace.getFormattedAddress()
                        );
                tripDestinationsManager.addLocation(location, new TripDestinationsManager.AddLocationCallback() {
                    @Override
                    public void onAddLocationResult(boolean success, Location resultLocation) {
                        if (success) {
                            tripDestinationsManager.addPlannedDestination(new PlannedDestination(selectedTrip.getId(),
                                    resultLocation.getId(),
                                    destinationNo,
                                    beginTime,endTime,note), new TripDestinationsManager.AddDestinationCallback() {
                                @Override
                                public void onAddDestinationResult(boolean success, String code) {
                                    if (success) {
                                        destinationNo++;
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                    else {
                                        if (code.equals("22007")) {
                                            Toast.makeText(requireContext(), "Vui lòng điền đúng format thời gian : HH:mm", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}