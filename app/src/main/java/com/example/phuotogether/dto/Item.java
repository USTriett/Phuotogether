package com.example.phuotogether.dto;
import java.io.Serializable;
public class Item implements Serializable {
    private int tripID;
    private boolean isChecked;
    private int itemNo;
    private String name;

    // Constructor
    public Item(int tripID, int itemNo, String name) {
        this.tripID = tripID;
        this.itemNo = itemNo;
        this.name = name;
    }

    // Getter
    public int getTripID() {
        return tripID;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
