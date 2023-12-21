package com.example.phuotogether.data_layer.trip.tripList;

import com.example.phuotogether.R;

import java.util.ArrayList;
import java.util.List;

public class TripListDatabase {
    private List<Trip> tripList;

    public TripListDatabase() {
        this.tripList = new ArrayList<>();
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void addTripList(String tripName, String tripTime, int tripImageID){
        tripList.add(new Trip(tripName,tripTime,tripImageID));
    }

}
