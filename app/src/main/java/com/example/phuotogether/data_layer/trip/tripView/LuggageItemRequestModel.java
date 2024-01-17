package com.example.phuotogether.data_layer.trip.tripView;

import java.util.List;

public class LuggageItemRequestModel {
    private int tripid;
    private List<String> items;

    public LuggageItemRequestModel(int tripid, List<String> items) {
        this.tripid = tripid;
        this.items = items;
    }


}
