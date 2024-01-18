package com.example.phuotogether.data_layer.trip.tripList;

public class UpdateTripSettingRequestModel {
    private int id;
    private String name;
    private String departureplace;
    private String arrivalplace;
    private String departuredate;
    private String arrivaldate;

    public UpdateTripSettingRequestModel(int tripid, String name, String departureplace, String arrivalplace, String departuredate, String arrivaldate) {
        this.id = tripid;
        this.name = name;
        this.departureplace = departureplace;
        this.arrivalplace = arrivalplace;
        this.departuredate = departuredate;
        this.arrivaldate = arrivaldate;
    }
}
