package com.example.phuotogether.dto;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable{
    private int id;
    private int userID;
    private String name;
    private String departurePlace;
    private String arrivalPlace;
    private Date departureDate;
    private Date arrivalDate;

    // Constructor
    public Trip(int id, int userID, String name, String departurePlace, String arrivalPlace, Date departureDate, Date arrivalDate) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    // Getter
    public int getId() {
        return id;
    }
    public int getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getDeparturePlace() {
        return departurePlace;
    }
    public String getArrivalPlace() {
        return arrivalPlace;
    }
    public Date getDepartureDate() {
        return departureDate;
    }
    public Date getArrivalDate() {
        return arrivalDate;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }
    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
