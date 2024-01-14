package com.example.phuotogether.service;



import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.data_layer.map.DirectionResponseModel;
import com.example.phuotogether.data_layer.map.GoogleResponseModel;
import com.example.phuotogether.dto.User;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import okhttp3.*;

public interface RetrofitAPI {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<DirectionResponseModel> getDirection(@Url String url);

    @GET
    Call<GoogleResponseModel> getPopularPlaces(@Url String url);

    @POST("user/get_user_by_account")
    Call<List<UserResponse>> getUserByAccount(@Query("emailortel") String emailortel, @Query("password") String password);
}
