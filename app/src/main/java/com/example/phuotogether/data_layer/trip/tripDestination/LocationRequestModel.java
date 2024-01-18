package com.example.phuotogether.data_layer.trip.tripDestination;

public class LocationRequestModel {
    private double latitude;
    private double longitude;
    private String address;
    private String name;

    public LocationRequestModel(double latitude, double longitude, String address, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.name = name;
    }
}
