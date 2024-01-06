package com.example.phuotogether.businesslogic_layer.trip.tripList;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;

import java.util.List;

public class TripListManager {
    private static TripListManager instance;
    private TripListDatabase tripListDatabase;

    private TripListManager() {
        tripListDatabase = new TripListDatabase();
    }

    public static TripListManager getInstance() {
        if (instance == null) {
            instance = new TripListManager();
        }
        return instance;
    }

    public List<Trip> getTripList() {
        return tripListDatabase.getTripList();
    }

    public void addTrip(String tripName, String startDate, String endDate, String startDes, String goalDes) {
        String date = startDate + " - " + endDate;
        int imageID = R.drawable.binhthuan;
        tripListDatabase.addTripList(tripName, date, imageID, startDes, goalDes, startDate, endDate);
    }

    public boolean isSuccessAddTrip(String tripName, String startDes, String goalDes, String startDate, String endDate) {
        if (tripName.isEmpty() || startDes.isEmpty() || goalDes.isEmpty() || startDes.isEmpty() || endDate.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public Trip getTripAtPosition(int position) {
        List<Trip> tripList = tripListDatabase.getTripList();
        if (position >= 0 && position < tripList.size()) {
            return tripList.get(position);
        } else {
            return null;
        }
    }
}

