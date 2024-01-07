package com.example.phuotogether.gui_layer.trip.destinationList;

import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;

import java.util.ArrayList;

public interface OnDataFetchedListener {
    void onDataFetched(ArrayList<GooglePlaceModel> popularPlaces);
}
