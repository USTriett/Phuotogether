package com.example.phuotogether.business_layer.map;

import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapPresenterListener {
    void onShowDirectionClicked(LatLng startLocation, LatLng endLocation);

    void onNearbyPlacesFetch(List<GooglePlaceModel> googlePlaceModelList);
}
