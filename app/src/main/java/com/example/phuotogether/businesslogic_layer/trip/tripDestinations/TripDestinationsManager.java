package com.example.phuotogether.businesslogic_layer.trip.tripDestinations;

import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripDestination.DestinationListDatabase;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.dto.Location;
import com.example.phuotogether.dto.PlannedDestination;

import java.util.List;

public class TripDestinationsManager {
    private static TripDestinationsManager instance;
    private DestinationListDatabase destinationListDatabase;

    public interface FetchDestinationCallback {
        void onFetchDestinationResult(boolean success, List<PlannedDestination> destinationList);
    }
    public interface FetchLocationCallback {
        void onFetchLocationResult(boolean success, Location location);
    }
    public interface AddLocationCallback {
        void onAddLocationResult(boolean success, Location location);
    }
    public interface AddDestinationCallback {
        void onAddDestinationResult(boolean success, String code);
    }
    public interface DeleteDestinationCallback {
        void onDeleteDestinationResult(boolean success);
    }

    private TripDestinationsManager() {
        destinationListDatabase = new DestinationListDatabase();
    }

    public static TripDestinationsManager getInstance() {
        if (instance == null) {
            instance = new TripDestinationsManager();
        }
        return instance;
    }

    public void getDestinationList(int tripId, FetchDestinationCallback callback) {
        destinationListDatabase.fetchDestinationList(tripId, new DestinationListDatabase.FetchDestinationCallback() {
            @Override
            public void onFetchDestinationResult(boolean success, List<PlannedDestination> destinationList) {
                if (success) {
                    callback.onFetchDestinationResult(true, destinationList);
                }
                else {
                    callback.onFetchDestinationResult(false, null);
                }
            }
        });
    }

    public void getLocation(int locationId, FetchLocationCallback callback) {
        Log.d("TripDestinationsManager", "getLocation: " + locationId);
        destinationListDatabase.fetchLocation(locationId, new DestinationListDatabase.FetchLocationCallback() {
            @Override
            public void onFetchLocationResult(boolean success, Location location) {
                if (success) {
                    callback.onFetchLocationResult(true, location);
                }
                else {
                    callback.onFetchLocationResult(false, null);
                }
            }
        });
    }

    public void addPlannedDestination(PlannedDestination destination, AddDestinationCallback callback) {
        destinationListDatabase.addPlannedDestination(destination, new DestinationListDatabase.AddDestinationCallback() {
            @Override
            public void onAddDestinationResult(boolean success, String code) {
                if (success) {
                    callback.onAddDestinationResult(true, "0");
                }
                else {
                    callback.onAddDestinationResult(false, code);
                }
            }
        });
    }
    public void addLocation(Location location, AddLocationCallback callback) {
        destinationListDatabase.addLocation(location, new DestinationListDatabase.AddLocationCallback() {
            @Override
            public void onAddLocationResult(boolean success, Location resultLocation) {
                if (success) {
                    callback.onAddLocationResult(true, resultLocation);
                    Log.d("TripDestinationsManager", "onAddLocationResult: " + resultLocation.getName());
                }
                else {
                    callback.onAddLocationResult(false,resultLocation);
                }
            }
        });
    }

    public void deleteDestination(PlannedDestination destination, DeleteDestinationCallback callback) {
        destinationListDatabase.deleteDestination(destination, new DestinationListDatabase.DeleteDestinationCallback() {
            @Override
            public void onDeleteDestinationResult(boolean success) {
                if (success) {
                    callback.onDeleteDestinationResult(true);
                }
                else {
                    callback.onDeleteDestinationResult(false);
                }
            }
        });
    }

//    public PlannedDestination getDestinationAtPosition(int position) {
//        List<PlannedDestination> destinationList = destinationListDatabase.getDestinationList();
//        if (position >= 0 && position < destinationList.size()) {
//            return destinationList.get(position);
//        } else {
//            return null;
//        }
//    }


}
