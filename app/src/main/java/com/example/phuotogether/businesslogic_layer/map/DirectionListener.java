package com.example.phuotogether.businesslogic_layer.map;

import com.example.phuotogether.data_layer.map.DirectionStepModel;

import java.util.List;

public interface DirectionListener {
    void onDirectionReceived(String startLocation, String endLocation,
                             String time, String distance, List<DirectionStepModel> steps);
}
