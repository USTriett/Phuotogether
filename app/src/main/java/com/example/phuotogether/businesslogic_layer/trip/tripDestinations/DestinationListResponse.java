package com.example.phuotogether.businesslogic_layer.trip.tripDestinations;

import com.google.gson.annotations.SerializedName;

public class DestinationListResponse {
    @SerializedName("tripid")
    private int tripId;

    @SerializedName("destinationno")
    private int destinationId;

    @SerializedName("locationid")
    private int locationId;

    @SerializedName("begintime")
    private String beginTime;

    @SerializedName("endtime")
    private String endTime;

    @SerializedName("note")
    private String note;

    public DestinationListResponse(int tripId, int destinationId, int locationId, String beginTime, String endTime, String note) {
        this.tripId = tripId;
        this.destinationId = destinationId;
        this.locationId = locationId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.note = note;
    }

    public int getTripId() {
        return tripId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public int getLocationId() {
        return locationId;
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



}
