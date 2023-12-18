package com.example.phuotogether.businesslogic_layer.trip.tripList;

import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;

import java.util.List;

public class TripListManager {
    private TripListDatabase tripListDatabase;

    public TripListManager(){
        tripListDatabase = new TripListDatabase();
    }

    public List<Trip> getTripList() {
        return tripListDatabase.getTripList();
    }
}
