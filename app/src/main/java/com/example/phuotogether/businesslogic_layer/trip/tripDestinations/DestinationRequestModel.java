package com.example.phuotogether.businesslogic_layer.trip.tripDestinations;

public class DestinationRequestModel {
    private int tripid;
    private int locationid;
    private String begintime;
    private String endtime;
    private String note;

    public DestinationRequestModel(int tripid, int locationid, String begintime, String endtime, String note) {
        this.tripid = tripid;
        this.locationid = locationid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.note = note;
    }

}
