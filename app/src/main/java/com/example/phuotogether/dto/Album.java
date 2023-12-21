package com.example.phuotogether.dto;

import java.io.Serializable;
public class Album implements Serializable {
    private Integer id;
    private String name;
    private Integer tripID;
    private Integer passedPointNo;

    // Constructor
    public Album(Integer id, String name, Integer tripID, Integer passedPointNo) {
        this.id = id;
        this.name = name;
        this.tripID = tripID;
        this.passedPointNo = passedPointNo;
    }

    // Getter
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getTripID() {
        return tripID;
    }
    public int getPassedPointNo() {
        return passedPointNo;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
    public void setPassedPointNo(int passedPointNo) {
        this.passedPointNo = passedPointNo;
    }

}
