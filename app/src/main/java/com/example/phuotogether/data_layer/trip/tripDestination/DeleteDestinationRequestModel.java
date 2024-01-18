package com.example.phuotogether.data_layer.trip.tripDestination;

public class DeleteDestinationRequestModel {
    private int tripid;
    private int destinationno;

    public DeleteDestinationRequestModel(int tripid, int destinationno) {
        this.tripid = tripid;
        this.destinationno = destinationno;
    }
}
