package com.example.phuotogether.data_layer.trip.tripView;

public class Luggage {
    private String name;
    private boolean isChecked;
    private int tripID;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Luggage(String name, boolean isChecked, int tripID, int ID) {
        this.name = name;
        this.isChecked = isChecked;
        this.tripID = tripID;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
