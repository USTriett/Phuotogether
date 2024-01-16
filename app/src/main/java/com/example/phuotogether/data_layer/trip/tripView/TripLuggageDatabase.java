package com.example.phuotogether.data_layer.trip.tripView;

import java.util.ArrayList;
import java.util.List;

public class TripLuggageDatabase {
    private List<Luggage> tripLuggage;

    public TripLuggageDatabase() {
        this.tripLuggage = new ArrayList<>();
    }

    public List<Luggage> getLuggageListByID(int tripID){
        List<Luggage> res = new ArrayList<>();
        for (int i = 0; i < tripLuggage.size(); i++){
            if (tripLuggage.get(i).getTripID() == tripID){
                res.add(tripLuggage.get(i));
            }
        }
        return res;
    }

    public void addTripLuggage(String name, Boolean isChecked, int tripID, int ID){
        tripLuggage.add(new Luggage(name,isChecked,tripID,ID));
    }

    public void removeTripLuggage(String name, int tripID) {
        for (int i = 0; i < tripLuggage.size(); i++){
            if (tripLuggage.get(i).getTripID() == tripID && tripLuggage.get(i).getName() == name){
                tripLuggage.remove(i);
            }
        }
    }
}
