package com.example.phuotogether.businesslogic_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.map.GoogleResponseModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripList.TripListDatabase;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripListManager {
    private static TripListManager instance;
    private TripListDatabase tripListDatabase;
    private User user;

    public interface FetchTripListCallback {
        void onFetchTripResult(boolean success, List<Trip> tripList);
    }
    public interface DeleteTripCallback {
        void onDeleteTripResult(boolean success);
    }
    public interface AddTripCallback {
        void onAddTripResult(boolean success, String code);
    }

    public interface UpdateTripCallback {
        void onUpdateTripResult(boolean success);
    }
    public interface OnPlaceFetchedListener {
        void onDataFetched(GooglePlaceModel googlePlaceModel);
    }

    private TripListManager(User user) {
        tripListDatabase = new TripListDatabase();
        this.user = user;
    }

    public static TripListManager getInstance(User user) {
        Log.d("TripListManager", "getInstance: " + user.getId());
        if (instance == null) {
            instance = new TripListManager(user);
        }
        return instance;
    }

    public void getTripList(FetchTripListCallback callback) {
        tripListDatabase.fetchTripList(user.getId(), new TripListDatabase.FetchTripCallback() {
            @Override
            public void onFetchTripResult(boolean success, List<Trip> tripList) {
                if (success) {
                    callback.onFetchTripResult(true, tripList);
                }
                else {
                    callback.onFetchTripResult(false, null);
                }
            }
        });
    }

    public void deleteTrip(Trip trip, DeleteTripCallback callback) {
        tripListDatabase.deleteTrip(trip, new TripListDatabase.DeleteTripCallback() {
            @Override
            public void onDeleteTripResult(boolean success) {
                if (success) {
                    callback.onDeleteTripResult(true);
                }
                else {
                    callback.onDeleteTripResult(false);
                }
            }
        });
    }

    public void addTrip(User user, String tripName, String startDate, String endDate, String startDes, String goalDes,
                        AddTripCallback callback) {
        Log.d("TripListManager", "addTrip: " +user.getId());
        String date = startDate + " - " + endDate;
        int imageID = R.drawable.binhthuan;
        tripListDatabase.addTripList(user,tripName, date, imageID, startDes, goalDes, startDate, endDate, new TripListDatabase.AddTripCallback() {
            @Override
            public void onAddTripResult(boolean success, String code) {
                if (success) {
                    Log.d("TripListManager", "onAddTripResult: " + success);
                    callback.onAddTripResult(true, "0");
                }
                else {
                    callback.onAddTripResult(false, code);
                }
            }
        });
    }

    public void updateTrip(Trip trip, String tripName, String startDate, String endDate, String startDes, String goalDes,
                           UpdateTripCallback callback){
        tripListDatabase.updateTrip(trip, tripName, startDes, goalDes, startDate, endDate, new TripListDatabase.UpdateTripCallBack() {
            @Override
            public void onUpdateTripResult(boolean success) {
                if (success) {
                    Log.d("TripListManager", "onAddTripResult: " + success);
                    callback.onUpdateTripResult(true);
                }
                else {
                    callback.onUpdateTripResult(false);
                }
            }
        });
    }


    public void getPlace(String placeName, OnPlaceFetchedListener callback) {
        RetrofitAPI retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + placeName + "&key=" + "AIzaSyCl2PBKLn0jILonkq3kLx7Qw8CSwgUW6_o";
        Log.d("SearchPlacesActivity", "fetchPopularPlaces: " + url);
        retrofitAPI.getPlaceDetail(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                if (response.isSuccessful()) {
                    GoogleResponseModel googleResponseModel = response.body();
                    if (googleResponseModel.getError() == null) {
                        Log.d("SearchPlacesActivity", "onResponse: " + googleResponseModel.getGooglePlaceModelList().size());
                        ArrayList<GooglePlaceModel> data = new ArrayList<>(googleResponseModel.getGooglePlaceModelList());
                        Log.d("SearchPlacesActivity", "onResponse: " + data.size());
                        callback.onDataFetched(data.get(0));
                    }
                    else {
                        Log.d("SearchPlacesActivity", "onResponse: " + googleResponseModel.getError());
                    }
                }
                else {
                    Log.d("SearchPlacesActivity", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GoogleResponseModel> call, Throwable t) {
                Log.d("SearchPlacesActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}
