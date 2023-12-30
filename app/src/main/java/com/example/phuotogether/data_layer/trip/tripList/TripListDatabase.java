package com.example.phuotogether.data_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;

import java.util.ArrayList;
import java.util.List;

public class TripListDatabase {
    private List<Trip> tripList;

    public TripListDatabase() {

        this.tripList = new ArrayList<>();
        tripList.add(new Trip("Tam Ky", "time", R.drawable.binhthuan));
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void addTripList(String tripName, String tripTime, int tripImageID){
//        Log.d("Size1", String.valueOf(tripList.size()));
        tripList.add(new Trip(tripName,tripTime,tripImageID));
//        Log.d("Size2", String.valueOf(tripList.size()));
    }

}
