package com.example.phuotogether.data_layer.trip.tripDestination;

import android.util.Log;

import com.example.phuotogether.dto.PlannedDestination;

import java.util.ArrayList;
import java.util.List;

public class DestinationListDatabase {
    private List<PlannedDestination> destinationList;

    public DestinationListDatabase() {
        this.destinationList = new ArrayList<>();
    }

    public List<PlannedDestination> getDestinationList() {
        return destinationList;
    }

    public void addDestinationList(PlannedDestination destination) {
        Log.d("DestinationListDatabase", "addDestinationList: " + destination.getLocationName());
        destinationList.add(destination);
    }
}
