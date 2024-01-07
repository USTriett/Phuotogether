package com.example.phuotogether.data_layer.trip.tripList;

import java.io.Serializable;

public class Trip implements Serializable {
    private String tripName;
    private String tripTime;
    private String departurePlace;
    private String arrivalPlace;
    private String startDate;
    private String endDate;
    private int tripImageID;

    public Trip(String tripName, String tripTime, int tripImageID,
                String departurePlace, String arrivalPlace, String startDate, String endDate) {
        this.tripName = tripName;
        this.tripTime = tripTime;
        this.tripImageID = tripImageID;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
