package com.example.phuotogether.data_layer.trip.tripList;

public class AddTripRequestModel {
    private int userid;
    private String name;
    private String departureplace;
    private String arrivalplace;
    private String departuredate;
    private String arrivaldate;

    public AddTripRequestModel(int userid, String name, String departureplace, String arrivalplace, String departuredate, String arrivaldate) {
        this.userid = userid;
        this.name = name;
        this.departureplace = departureplace;
        this.arrivalplace = arrivalplace;
        this.departuredate = departuredate;
        this.arrivaldate = arrivaldate;
    }
}
