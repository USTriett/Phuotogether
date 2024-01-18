package com.example.phuotogether.data_layer.trip.tripView;

import android.util.Log;

import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.data_layer.trip.tripList.TripResponse;
import com.example.phuotogether.dto.Item;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripLuggageDatabase {
    private List<Item> tripLuggage;

    public TripLuggageDatabase() {
        this.tripLuggage = new ArrayList<>();
    }

    public  interface FetchItemCallback {
        void onFetchItemResult(boolean success, List<Item> itemList);
    }

    public interface AddItemCallback {
        void onAddItemResult(boolean success);
    }

    public void updateItemList(int tripId, List<String> items, TripLuggageDatabase.AddItemCallback callback) {
        Log.d("TripLuggageDatabase", "updateItemList: " + tripId);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        LuggageItemRequestModel luggageItemRequestModel = new LuggageItemRequestModel(tripId, items);
        Call<List<LuggageItemResponse>> call = myApi.updateItems(luggageItemRequestModel);
        call.enqueue(new Callback<List<LuggageItemResponse>>() {
            @Override
            public void onResponse(Call<List<LuggageItemResponse>> call, Response<List<LuggageItemResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onAddItemResult(true);
                }
                else {
                    callback.onAddItemResult(false);
                }
            }

            @Override
            public void onFailure(Call<List<LuggageItemResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void fetchItemList(int tripId, TripLuggageDatabase.FetchItemCallback callback) {
        Log.d("TripLuggageDatabase", "fetchItemList: " + tripId);
        tripLuggage.clear();
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<LuggageItemResponse>> call = myApi.getItems(tripId);
        call.enqueue(new Callback<List<LuggageItemResponse>>() {
            @Override
            public void onResponse(Call<List<LuggageItemResponse>> call, Response<List<LuggageItemResponse>> response) {
                if (response.isSuccessful()) {
                    List<LuggageItemResponse> itemResponses = response.body();
                    for (LuggageItemResponse itemResponse : itemResponses) {
                        Item item = new Item(tripId, itemResponse.getItemno(), itemResponse.getName());
                        tripLuggage.add(item);
                    }
                    callback.onFetchItemResult(true, tripLuggage);
                }
                else {
                    callback.onFetchItemResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<List<LuggageItemResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void addTripLuggage(String name, Boolean isChecked, int tripID, int ID){
        tripLuggage.add(new Item(tripID, ID, name));
    }

    public void removeTripLuggage(String name, int tripID) {
        for (int i = 0; i < tripLuggage.size(); i++){
            if (tripLuggage.get(i).getTripID() == tripID && tripLuggage.get(i).getName() == name){
                tripLuggage.remove(i);
            }
        }
    }
}
