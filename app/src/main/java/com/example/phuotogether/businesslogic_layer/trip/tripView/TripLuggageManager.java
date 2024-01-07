package com.example.phuotogether.businesslogic_layer.trip.tripView;

import com.example.phuotogether.data_layer.trip.tripView.Luggage;
import com.example.phuotogether.data_layer.trip.tripView.TripLuggageDatabase;

import java.util.List;

public class TripLuggageManager {
    private static TripLuggageManager instance;
    private TripLuggageDatabase tripLuggageDatabase;

    public static TripLuggageManager getInstance(){
        if (instance == null){
            instance = new TripLuggageManager();
        }
        return instance;
    }

    public TripLuggageManager() {
        this.tripLuggageDatabase = new TripLuggageDatabase();
    }

    public List<Luggage> getLuggageListByID(int tripID){
        return tripLuggageDatabase.getLuggageListByID(tripID);
    }

    public void addLuggage(String name, Boolean isChecked, int tripID, int ID){
        tripLuggageDatabase.addTripLuggage(name, isChecked, tripID, ID);
    }

    public void removeLuggage(String name, int tripID){
        tripLuggageDatabase.removeTripLuggage(name, tripID);
    }
}
