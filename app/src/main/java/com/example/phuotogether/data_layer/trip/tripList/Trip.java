package com.example.phuotogether.data_layer.trip.tripList;

public class Trip {
    private String tripName;
    private String tripTime;
    private int tripImageID;

    public Trip(String tripName, String tripTime, int tripImageID) {
        this.tripName = tripName;
        this.tripTime = tripTime;
        this.tripImageID = tripImageID;
    }

    public int getTripImageID() {
        return tripImageID;
    }

    public void setTripImageID(int tripImageID) {
        this.tripImageID = tripImageID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }
}
