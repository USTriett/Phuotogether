package com.example.phuotogether.service;



import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.DestinationListResponse;
import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.DestinationRequestModel;
import com.example.phuotogether.data_layer.auth.LoginRequestModel;
import com.example.phuotogether.data_layer.auth.SignupRequestModel;
import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.data_layer.map.DirectionResponseModel;
import com.example.phuotogether.data_layer.map.GoogleResponseModel;
import com.example.phuotogether.data_layer.trip.tripDestination.DeleteDestinationRequestModel;
import com.example.phuotogether.data_layer.trip.tripDestination.LocationRequestModel;
import com.example.phuotogether.data_layer.trip.tripDestination.LocationResponse;
import com.example.phuotogether.data_layer.trip.tripList.AddTripRequestModel;
import com.example.phuotogether.data_layer.trip.tripList.DeleteTripRequestModel;
import com.example.phuotogether.data_layer.trip.tripList.TripResponse;
import com.example.phuotogether.data_layer.trip.tripList.UpdateTripSettingRequestModel;
import com.example.phuotogether.data_layer.trip.tripView.LuggageItemRequestModel;
import com.example.phuotogether.data_layer.trip.tripView.LuggageItemResponse;
import com.example.phuotogether.data_layer.user.AvatarUserRequestModel;
import com.example.phuotogether.dto.Trip;
import com.example.phuotogether.dto.User;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import okhttp3.*;
import retrofit2.http.FormUrlEncoded;
public interface RetrofitAPI {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<DirectionResponseModel> getDirection(@Url String url);

    @GET
    Call<GoogleResponseModel> getPopularPlaces(@Url String url);

    @GET
    Call<GoogleResponseModel> getPlaceDetail(@Url String url);

    // auth ---------------------------------------------------
    @POST("user/get_user_by_account")
    Call<List<UserResponse>> getUserByAccount(@Body LoginRequestModel loginRequest);

    @POST("user/insert_user")
    Call<List<UserResponse>> insertUser(@Body SignupRequestModel signupRequest);


    // trip ---------------------------------------------------
    @GET("/trip/get_trips_by_userid")
    Call<List<TripResponse>> getTripsByUserId(@Query("userid") int userId);

    @POST("trip/insert_trip")
    Call<List<TripResponse>> insertTrip(@Body AddTripRequestModel addTripRequestModel);

    @HTTP(method = "DELETE", path = "trip/delete_trip", hasBody = true)
    Call<List<TripResponse>> deleteTrip(@Body DeleteTripRequestModel deleteTripRequestModel);

    @HTTP(method = "PUT", path = "trip/update_trip", hasBody = true)
    Call<List<TripResponse>> updateTripSetting(@Body UpdateTripSettingRequestModel updateTripSettingRequestModel);



    // item ---------------------------------------------------
    @GET("/item/get_items")
    Call<List<LuggageItemResponse>> getItems(@Query("tripid") int tripId);

    @POST("item/update_items")
    Call<List<LuggageItemResponse>> updateItems(@Body LuggageItemRequestModel luggageItemRequestModel);

//    @HTTP(method = "DELETE", path = "trip/delete_trip", hasBody = true)
//    Call<List<LuggageItemResponse>> deleteItem(@Body DeleteTripRequestModel deleteTripRequestModel);

    // planned destination ---------------------------------------------------
    @GET("/planned_destination/get_planned_destinations_by_tripid")
    Call<List<DestinationListResponse>> getPlannedDestinationsByTripId(@Query("tripid") int tripId);

    @POST("planned_destination/insert_planned_destination")
    Call<List<DestinationListResponse>> insertPlannedDestination(@Body DestinationRequestModel destinationRequestModel);

    @HTTP(method = "DELETE", path = "planned_destination/delete_planned_destination", hasBody = true)
    Call<List<DestinationListResponse>> deleteTrip(@Body DeleteDestinationRequestModel deleteDestinationRequestModel);

    // location ---------------------------------------------------
    @GET("/location/get_location")
    Call<List<LocationResponse>> getLocation(@Query("id") int locationId);

    @POST("location/insert_location")
    Call<List<LocationResponse>> insertLocation(@Body LocationRequestModel locationRequestModel);


    @POST("user/update_avatar")
    Call<List<Response>> updateAvatar(@Body AvatarUserRequestModel avatarUserRequestModel);
}
