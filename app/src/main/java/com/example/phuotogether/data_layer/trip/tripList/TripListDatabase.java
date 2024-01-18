package com.example.phuotogether.data_layer.trip.tripList;

import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.io.IOException;
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
    public interface DeleteTripCallback {
        void onDeleteTripResult(boolean success);
    }
    public interface AddTripCallback {
        void onAddTripResult(boolean success);
    }

    public interface UpdateTripCallBack{
        void onUpdateTripResult(boolean success);
    }
    public TripListDatabase() {
        this.tripList = new ArrayList<>();
    }


    public void addTripList(User user,String tripName, String tripTime, int tripImageID, String departurePlace, String arrivalPlace, String startDate, String endDate,
                            AddTripCallback callback){
        Log.d("TripListDatabase", "addTripList: " + user.getFullName());
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        AddTripRequestModel addTripRequestModel = new AddTripRequestModel(user.getId(), tripName, departurePlace, arrivalPlace, startDate, endDate);
        Call<List<TripResponse>> call = myApi.insertTrip(addTripRequestModel);

        call.enqueue(new Callback<List<TripResponse>>() {
            @Override
            public void onResponse(Call<List<TripResponse>> call, Response<List<TripResponse>> response) {
                if (!response.isSuccessful()) {
                    try {
                        Log.d("TripListDatabase", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onAddTripResult(false);
                }
                else {
                    Log.d("TripListDatabase", "onResponse: " + response.body().get(0).getName());
                    callback.onAddTripResult(true);
                }
            }

            @Override
            public void onFailure(Call<List<TripResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void updateTrip(Trip trip, String tripName, String departurePlace, String arrivalPlace, String startDate, String endDate,
                           UpdateTripCallBack callback){
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        UpdateTripSettingRequestModel updateTripSettingRequestModel = new UpdateTripSettingRequestModel(trip.getId(), tripName, departurePlace, arrivalPlace, startDate, endDate);

        Call<List<TripResponse>> call = myApi.updateTripSetting(updateTripSettingRequestModel);

        call.enqueue(new Callback<List<TripResponse>>() {
            @Override
            public void onResponse(Call<List<TripResponse>> call, Response<List<TripResponse>> response) {
                if (!response.isSuccessful()) {
                    try {
                        Log.d("TripListDatabase", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onUpdateTripResult(false);
                }
                else {
                    Log.d("TripListDatabase", "onResponse: " + response.body().get(0).getName());
                    callback.onUpdateTripResult(true);
                }
            }

            @Override
            public void onFailure(Call<List<TripResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void deleteTrip(Trip trip, DeleteTripCallback callback) {
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        DeleteTripRequestModel deleteTripRequestModel = new DeleteTripRequestModel(trip.getId());
        Call<List<TripResponse>> call = myApi.deleteTrip(deleteTripRequestModel);
        call.enqueue(new Callback<List<TripResponse>>() {
            @Override
            public void onResponse(Call<List<TripResponse>> call, Response<List<TripResponse>> response) {
                if (!response.isSuccessful()) {
                    try {
                        Log.d("TripListDatabase", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("TripListDatabase", "onResponse: " + response.body().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<TripResponse>> call, Throwable t) {
                Log.d("TripListDatabase", "onFailure: " + t.getMessage());
            }
        });
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
                                tripResponse.getArrivalDate(),
                                tripResponse.getDeparturePlace(),
                                tripResponse.getArrivalPlace());
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