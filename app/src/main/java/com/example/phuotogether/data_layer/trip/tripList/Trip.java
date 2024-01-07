package com.example.phuotogether.data_layer.trip.tripList;

import com.example.phuotogether.data_layer.trip.tripView.Luggage;

import java.util.List;

public class Trip {
    private String tripName;
    private String tripTime;
    private int tripImageID;
    private int ID;

    public Trip(String tripName, String tripTime, int tripImageID, int tripID) {
        this.tripName = tripName;
        this.tripTime = tripTime;
        this.tripImageID = tripImageID;
        this.ID = tripID;
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

    public int getTripID() {
        return ID;
    }

    public void setTripID(int tripID) {
        this.ID = tripID;
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
