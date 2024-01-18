package com.example.phuotogether.data_layer.trip.tripView;

import com.google.gson.annotations.SerializedName;

public class LuggageItemResponse {
    @SerializedName("itemno")
    private int itemno;

    @SerializedName("name")
    private String name;

    public LuggageItemResponse(int itemno, String name) {
        this.itemno = itemno;
        this.name = name;
    }

    public int getItemno() {
        return itemno;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
