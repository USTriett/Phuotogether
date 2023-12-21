package com.example.phuotogether.dto;
import java.io.Serializable;
public class PlannedDestination implements Serializable {
    private int tripID;
    private int locationID;
    private int destinationNo;
    private String beginTime;
    private String endTime;
    private String note;

    // Constructor
    public PlannedDestination(int tripID, int locationID, int destinationNo, String beginTime, String endTime, String note) {
        this.tripID = tripID;
        this.locationID = locationID;
        this.destinationNo = destinationNo;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.note = note;
    }

    // Getter
    public int getTripID() {
        return tripID;
    }
    public int getLocationID() {
        return locationID;
    }
    public int getDestinationNo() {
        return destinationNo;
    }
    public String getBeginTime() {
        return beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getNote() {
        return note;
    }

    // Setter
    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    public void setDestinationNo(int destinationNo) {
        this.destinationNo = destinationNo;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
