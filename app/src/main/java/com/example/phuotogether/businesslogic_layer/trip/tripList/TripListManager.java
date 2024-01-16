package com.example.phuotogether.businesslogic_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.dto.User;

import java.util.List;

public class TripListManager {
    private static TripListManager instance;
    private TripListDatabase tripListDatabase;
    private User user;
    private List<Trip> tripList;

    public interface FetchTripListCallback {
        void onFetchTripResult(boolean success, List<Trip> tripList);
    }

    private TripListManager(User user) {
        tripListDatabase = new TripListDatabase();
        this.user = user;
    }

    public static TripListManager getInstance(User user) {
        Log.d("TripListManager", "getInstance: " + user.getId());
        if (instance == null) {
            instance = new TripListManager(user);
        }
        return instance;
    }

    public void getTripList(FetchTripListCallback callback) {
        tripListDatabase.fetchTripList(user.getId(), new TripListDatabase.FetchTripCallback() {
            @Override
            public void onFetchTripResult(boolean success, List<Trip> tripList) {
                if (success) {
                    callback.onFetchTripResult(true, tripList);
                }
                else {
                    callback.onFetchTripResult(false, null);
                }
            }
        });
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
//        List<Trip> tripList = tripListDatabase.getTripList();
        if (position >= 0 && position < tripList.size()) {
            return tripList.get(position);
        } else {
            return null;
        }
    }
}
