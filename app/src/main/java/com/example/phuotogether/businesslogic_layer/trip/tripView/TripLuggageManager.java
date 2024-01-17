package com.example.phuotogether.businesslogic_layer.trip.tripView;

import android.util.Log;

import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.data_layer.trip.tripView.Luggage;
import com.example.phuotogether.data_layer.trip.tripView.TripLuggageDatabase;
import com.example.phuotogether.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class TripLuggageManager {
    private static TripLuggageManager instance;
    private TripLuggageDatabase tripLuggageDatabase;
    private int tripID;



    public interface FetchItemCallback {
        void onFetchItemResult(boolean success, List<Item> luggageList);
    }

    public interface AddItemCallback {
        void onAddItemResult(boolean success);
    }

    public static TripLuggageManager getInstance(int tripID){
        if (instance == null){
            instance = new TripLuggageManager(tripID);
        }
        return instance;
    }

    public TripLuggageManager(int tripID) {
        this.tripLuggageDatabase = new TripLuggageDatabase();
        this.tripID = tripID;
    }

    public void getItemList(FetchItemCallback callback) {
        Log.d("TripLuggageManager", "getItemList: " + tripID);
        tripLuggageDatabase.fetchItemList(tripID, new TripLuggageDatabase.FetchItemCallback() {
            @Override
            public void onFetchItemResult(boolean success, List<Item> itemList) {
                if (success) {
                    callback.onFetchItemResult(true, itemList);
                }
                else {
                    callback.onFetchItemResult(false, null);
                }
            }
        });
    }
    public void updateItemList(int id, List<Item> listItem, TripLuggageManager.AddItemCallback addItemCallback) {
        Log.d("TripLuggageManager", "updateItemList: " + tripID);
        List<String> itemList = listItemToListString(listItem);
        Log.d("TripLuggageManager", "updateItemList: " + itemList.size());
        tripLuggageDatabase.updateItemList(id, itemList, new TripLuggageDatabase.AddItemCallback() {
            @Override
            public void onAddItemResult(boolean success) {
                if (success) {
                    addItemCallback.onAddItemResult(true);
                }
                else {
                    addItemCallback.onAddItemResult(false);
                }
            }
        });
    }

    private List<String> listItemToListString(List<Item> listItem) {
        List<String> itemList = new ArrayList<>();
        for (Item item : listItem) {
            itemList.add(item.getName());
        }
        return itemList;
    }

    public void addLuggage(String name, Boolean isChecked, int tripID, int ID){
        tripLuggageDatabase.addTripLuggage(name, isChecked, tripID, ID);
    }

    public void removeLuggage(String name, int tripID){
        tripLuggageDatabase.removeTripLuggage(name, tripID);
    }
}
