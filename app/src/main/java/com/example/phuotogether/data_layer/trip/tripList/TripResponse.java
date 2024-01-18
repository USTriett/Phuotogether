package com.example.phuotogether.data_layer.trip.tripList;

import com.google.gson.annotations.SerializedName;

public class TripResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("departuredate")
    private String departureDate;

    @SerializedName("arrivaldate")
    private String arrivalDate;

    @SerializedName("departureplace")
    private String departurePlace;

    @SerializedName("arrivalplace")
    private String arrivalPlace;

    // Add getters and setters as needed

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

}
