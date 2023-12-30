package com.example.phuotogether.dto;
import java.io.Serializable;
public class PassedPoint implements Serializable{
    private int id;
    private int tripID;
    private int locationID;
    private String time;

    // Constructor
    public PassedPoint(int id, int tripID, int locationID, String time) {
        this.id = id;
        this.tripID = tripID;
        this.locationID = locationID;
        this.time = time;
    }

    // Getter
    public int getId() {
        return id;
    }
    public int getTripID() {
        return tripID;
    }
    public int getLocationID() {
        return locationID;
    }
    public String getTime() {
        return time;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
