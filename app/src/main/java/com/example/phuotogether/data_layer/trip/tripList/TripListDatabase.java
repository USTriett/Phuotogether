package com.example.phuotogether.data_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripListDatabase {
    private List<Trip> tripList;
    private User user;
    public interface FetchTripCallback {
        void onFetchTripResult(boolean success, List<Trip> tripList);
    }

    public TripListDatabase() {
        this.tripList = new ArrayList<>();
    }


    public void addTripList(String tripName, String tripTime, int tripImageID, String departurePlace, String arrivalPlace, String startDate, String endDate){
        tripList.add(new Trip(tripName,tripTime,tripImageID,departurePlace,arrivalPlace,startDate,endDate));
    }

    public void fetchTripList(int userId, FetchTripCallback callback) {
        tripList.clear();
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<TripResponse>> call = myApi.getTripsByUserId(userId);
        call.enqueue(new Callback<List<TripResponse>>() {
            @Override
            public void onResponse(Call<List<TripResponse>> call, Response<List<TripResponse>> response) {
                if (response.isSuccessful()) {
                    List<TripResponse> tripResponses = response.body();
                    for (TripResponse tripResponse : tripResponses) {
                        Trip trip = new Trip(tripResponse.getId(), userId,
                                tripResponse.getName(),
                                tripResponse.getDepartureDate(),
                                tripResponse.getArrivalDate());
                        tripList.add(trip);
                    }
                    callback.onFetchTripResult(true, tripList);
                }
                else {
                    callback.onFetchTripResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<List<TripResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

}