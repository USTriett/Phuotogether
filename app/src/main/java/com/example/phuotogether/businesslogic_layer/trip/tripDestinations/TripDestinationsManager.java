package com.example.phuotogether.businesslogic_layer.trip.tripDestinations;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripDestination.DestinationListDatabase;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.dto.PlannedDestination;

import java.util.List;

public class TripDestinationsManager {
    private static TripDestinationsManager instance;
    private DestinationListDatabase destinationListDatabase;

    private TripDestinationsManager() {
        destinationListDatabase = new DestinationListDatabase();
    }

    public static TripDestinationsManager getInstance() {
        if (instance == null) {
            instance = new TripDestinationsManager();
        }
        return instance;
    }

    public List<PlannedDestination> getDestinationList() {
        return destinationListDatabase.getDestinationList();
    }

    public void addDestination(PlannedDestination destination) {
        destinationListDatabase.addDestinationList(destination);
    }


    public PlannedDestination getDestinationAtPosition(int position) {
        List<PlannedDestination> destinationList = destinationListDatabase.getDestinationList();
        if (position >= 0 && position < destinationList.size()) {
            return destinationList.get(position);
        } else {
            return null;
        }
    }
}
