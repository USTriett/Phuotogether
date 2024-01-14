package com.example.phuotogether.data_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;

import java.util.ArrayList;
import java.util.List;

public class TripListDatabase {
    private List<Trip> tripList;

    public TripListDatabase() {
        this.tripList = new ArrayList<>();
        tripList.add(new Trip("Tam Ky", "time", R.drawable.binhthuan,
                "HCM", "Tam Ky", "12/12/2023", "12/1/2024"));
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void addTripList(String tripName, String tripTime, int tripImageID, String departurePlace, String arrivalPlace, String startDate, String endDate){
        tripList.add(new Trip(tripName,tripTime,tripImageID,departurePlace,arrivalPlace,startDate,endDate));
    }

}