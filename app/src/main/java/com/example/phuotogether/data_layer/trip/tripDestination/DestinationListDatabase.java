package com.example.phuotogether.data_layer.trip.tripDestination;

import android.util.Log;

import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.DestinationListResponse;
import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.DestinationRequestModel;
import com.example.phuotogether.dto.Location;
import com.example.phuotogether.dto.PlannedDestination;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DestinationListDatabase {
    private List<PlannedDestination> destinationList;

    public interface FetchDestinationCallback {
        void onFetchDestinationResult(boolean success, List<PlannedDestination> destinationList);
    }

    public interface FetchLocationCallback {
        void onFetchLocationResult(boolean success, Location location);
    }

    public interface AddLocationCallback {
        void onAddLocationResult(boolean success, Location location);
    }

    public interface AddDestinationCallback {
        void onAddDestinationResult(boolean success, String code);
    }

    public interface DeleteDestinationCallback {
        void onDeleteDestinationResult(boolean success);
    }

    public DestinationListDatabase() {
        this.destinationList = new ArrayList<>();
    }

    public void fetchDestinationList(int tripId, DestinationListDatabase.FetchDestinationCallback callback) {
        Log.d("DestinationListDatabase", "fetchDestinationList: " + tripId);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<DestinationListResponse>> call = myApi.getPlannedDestinationsByTripId(tripId);
        call.enqueue(new retrofit2.Callback<List<DestinationListResponse>>() {
            @Override
            public void onResponse(Call<List<DestinationListResponse>> call, retrofit2.Response<List<DestinationListResponse>> response) {
                if (response.isSuccessful()) {
                    destinationList.clear();
                    for (DestinationListResponse destinationListResponse : response.body()) {
                        destinationList.add(new PlannedDestination(tripId,
                                destinationListResponse.getLocationId(),
                                destinationListResponse.getDestinationId(),
                                destinationListResponse.getBeginTime(),
                                destinationListResponse.getEndTime(),
                                destinationListResponse.getNote()));
                    }
                    callback.onFetchDestinationResult(true, destinationList);
                }
                else {
                    callback.onFetchDestinationResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<List<DestinationListResponse>> call, Throwable t) {
                Log.d("DestinationListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void addPlannedDestination(PlannedDestination destination, AddDestinationCallback callback) {
        Log.d("DestinationListDatabase", "addPlannedDestination: " + destination.getLocationName());
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<DestinationListResponse>> call = myApi.insertPlannedDestination(new DestinationRequestModel(destination.getTripID(),
                destination.getLocationID(),
                destination.getBeginTime(),
                destination.getEndTime(),
                destination.getNote()));
        call.enqueue(new retrofit2.Callback<List<DestinationListResponse>>() {
            @Override
            public void onResponse(Call<List<DestinationListResponse>> call, retrofit2.Response<List<DestinationListResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onAddDestinationResult(true, "0");
                }
                else {
                    String errorBodyString = null;
                    try {
                        errorBodyString = response.errorBody().string();
                        Log.d("UserDatabase", "Error Body: " + errorBodyString);

                        int codeStartIndex = errorBodyString.indexOf("'code': '") + "'code': '".length();
                        int codeEndIndex = errorBodyString.indexOf("'", codeStartIndex);
                        String errorCode = errorBodyString.substring(codeStartIndex, codeEndIndex);
                        Log.d("errorcode", errorCode);

                        callback.onAddDestinationResult(false, errorCode);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<DestinationListResponse>> call, Throwable t) {
                Log.d("DestinationListDatabase", "onFailure: " + t.getMessage());
                callback.onAddDestinationResult(false, null);
            }
        });
    }

    public void fetchLocation(int locationId, DestinationListDatabase.FetchLocationCallback callback) {
        Log.d("DestinationListDatabase", "fetchLocation: " + locationId);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<LocationResponse>> call = myApi.getLocation(locationId);
        call.enqueue(new retrofit2.Callback<List<LocationResponse>>() {
            @Override
            public void onResponse(Call<List<LocationResponse>> call, retrofit2.Response<List<LocationResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onFetchLocationResult(true, new Location(locationId,
                            response.body().get(0).getLatitude(),
                            response.body().get(0).getLongitude(),
                            response.body().get(0).getName(),
                            response.body().get(0).getAddress()));
                    Log.d("DestinationListDatabase", "onResponse: " + response.body().get(0).getName());
                }
                else {
                    Log.d("DestinationListDatabase", "onResponse: " + response.message());
                    callback.onFetchLocationResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<List<LocationResponse>> call, Throwable t) {
                Log.d("DestinationListDatabase", "onFailure: " + t.getMessage());
            }
        });
    }

    public void addLocation(Location location, AddLocationCallback callback) {
        Log.d("DestinationListDatabase", "addLocation: " + location.getName());
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<LocationResponse>> call = myApi.insertLocation(new LocationRequestModel(location.getLatitude(),
                location.getLongitude(),
                location.getAddress(),
                location.getName()));
        call.enqueue(new retrofit2.Callback<List<LocationResponse>>() {
            @Override
            public void onResponse(Call<List<LocationResponse>> call, retrofit2.Response<List<LocationResponse>> response) {
                if (response.isSuccessful()) {
                    location.setId(response.body().get(0).getId());
                    callback.onAddLocationResult(true, location);
                }
                else {
                    Log.d("DestinationListDatabase", "onResponse: " + response.message());
                    callback.onAddLocationResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<List<LocationResponse>> call, Throwable t) {
                Log.d("DestinationListDatabase", "onFailure: " + t.getMessage());
                callback.onAddLocationResult(false, null);
            }
        });
    }
    public void deleteDestination(PlannedDestination destination, DeleteDestinationCallback callback) {
        Log.d("DestinationListDatabase", "deleteDestination: " + destination.getLocationName());
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);
        Call<List<DestinationListResponse>> call = myApi.deleteTrip(new DeleteDestinationRequestModel(destination.getTripID(),
                destination.getDestinationNo()));
        call.enqueue(new retrofit2.Callback<List<DestinationListResponse>>() {
            @Override
            public void onResponse(Call<List<DestinationListResponse>> call, retrofit2.Response<List<DestinationListResponse>> response) {
                if (response.isSuccessful()) {
                    callback.onDeleteDestinationResult(true);
                }
                else {
                    callback.onDeleteDestinationResult(false);
                }
            }

            @Override
            public void onFailure(Call<List<DestinationListResponse>> call, Throwable t) {
                Log.d("DestinationListDatabase", "onFailure: " + t.getMessage());
                callback.onDeleteDestinationResult(false);
            }
        });
    }
}
