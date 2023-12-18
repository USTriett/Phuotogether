package com.example.phuotogether.data_layer.trip.tripList;

import com.example.phuotogether.R;

import java.util.ArrayList;
import java.util.List;

public class TripListDatabase {
    public List<Trip> getTripList() {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(new Trip("Bình thuận", "03/11/2023 - 06/11/2023", R.drawable.binhthuan));
        tripList.add(new Trip("Bình thuận", "03/11/2023 - 06/11/2023", R.drawable.binhthuan));

        return tripList;
    }
}
