package com.example.phuotogether.gui_layer.map;

import com.example.phuotogether.data_layer.map.GooglePlaceModel;

public interface NearLocationInterface {

    void onSaveClick(GooglePlaceModel googlePlaceModel);

    void onDirectionClick(GooglePlaceModel googlePlaceModel);
}
