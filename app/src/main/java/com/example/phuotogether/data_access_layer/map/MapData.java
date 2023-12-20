package com.example.phuotogether.data_access_layer.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapData extends Fragment {

    public static final int FINE_LOCATION_CODE = 101;
    private MapFragment mMapFragment;

    public MapData(MapFragment mapFragment) {
        this.mMapFragment = mapFragment;
    }

    public void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(mMapFragment.requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mMapFragment.requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions from the fragment
            mMapFragment.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
            return;
        }

        // Get the last known location
        Task<Location> task = LocationServices.getFusedLocationProviderClient(mMapFragment.requireActivity()).getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                Log.d("MapDataAccess", "getLastLocation: " + location.toString());
                if (mMapFragment instanceof MapDataListener) {
                    ((MapDataListener) mMapFragment).onLastLocationReceived(location);
                }
            } else {
                Log.d("MapDataAccess", "getLastLocation: location is null");
            }
        });
    }

    // Interface for callback to the fragment
    public interface MapDataListener {
        void onLastLocationReceived(Location location);
    }
}
