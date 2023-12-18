package com.example.phuotogether.gui_layer.map;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.business_layer.map.DirectionsManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LocationInfoFragment extends BottomSheetDialogFragment {
    private LatLng currentLocation;
    private DirectionsManager directionsManager;
    public static LocationInfoFragment newInstance(LatLng latLng, LatLng currentLocation, DirectionsManager directionsManager) {
        LocationInfoFragment fragment = new LocationInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("latLng", latLng);
        args.putParcelable("currentLocation", currentLocation);
        fragment.setDirectionsManager(directionsManager);
        fragment.setArguments(args);
        return fragment;
    }
    private void setDirectionsManager(DirectionsManager directionsManager) {
        this.directionsManager = directionsManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_info, container, false);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        // Retrieve LatLng from arguments
        LatLng latLng = getArguments().getParcelable("latLng");

        // Customize your dialog UI and set the location information
        TextView locationInfoTextView = rootView.findViewById(R.id.location_name);
        locationInfoTextView.setText("Location Info: " + latLng.toString());
        Button showDirectionsButton = rootView.findViewById(R.id.show_direction_btn);
        showDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDirection();
            }
        });

        return rootView;
    }

    public void showDirection() {
        LatLng currentLocation = getArguments().getParcelable("currentLocation");
        LatLng latLng = getArguments().getParcelable("latLng");
        Log.d("LocationInfoFragment", "showDirection: " + currentLocation.toString() + " " + latLng.toString());
        if (directionsManager == null) {
            Log.d("LocationInfoFragment", "showDirection: directionsManager is null");
        }
        directionsManager.getDirections(currentLocation, latLng);
        // Dismiss the bottom sheet dialog
        dismiss();
    }
}
