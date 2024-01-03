package com.example.phuotogether.service;



import com.example.phuotogether.data_access_layer.map.DirectionResponseModel;
import com.example.phuotogether.data_access_layer.map.GoogleResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<DirectionResponseModel> getDirection(@Url String url);
}
