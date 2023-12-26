package com.example.phuotogether.dto;
import java.io.Serializable;
public class Location implements Serializable{
    private int id;
    private double latitude;
    private double longitude;
    private String name;
    private String address;

    // Constructor
    public Location(int id, double latitude, double longitude, String name, String address) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }

    // Getter
    public int getId() {
        return id;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
