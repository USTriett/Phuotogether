package com.example.phuotogether.data_access_layer.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.phuotogether.gui_layer.map.MapActivity;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MapData {
    public int FINE_LOCATION_CODE = 101;
    private final MapActivity mapActivity;


    public MapData(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
    }

    public void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(mapActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mapActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mapActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
            return;
        }

        // Get the last known location
        Task<Location> task = LocationServices.getFusedLocationProviderClient(mapActivity).getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                   Log.d("MapDataAccess", "getLastLocation: " + location.toString());
                    mapActivity.onLastLocationReceived(location);

            } else {
                Log.d("MapDataAccess", "getLastLocation: location is null");
            }
        });
    }


}
