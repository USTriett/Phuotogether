package com.example.phuotogether.business_layer.map;



import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phuotogether.data_access_layer.map.DirectionLegModel;
import com.example.phuotogether.data_access_layer.map.DirectionResponseModel;
import com.example.phuotogether.data_access_layer.map.DirectionRouteModel;
import com.example.phuotogether.data_access_layer.map.DirectionStepModel;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DirectionsManager {

    private RequestQueue requestQueue;
    private GoogleMap googleMap;
    private String apiKey = "AIzaSyA9JmoH-4EPYNGNFqR7VHbJmSfx7ldbiec";
    private Polyline currentPolyline;
    private RetrofitAPI retrofitAPI;
    private MapPresenter mapPresenter;

    public DirectionsManager(RequestQueue requestQueue, GoogleMap googleMap, DirectionListener listener) {
        this.requestQueue = requestQueue;
        this.googleMap = googleMap;
        this.directionListener = listener;
    }

    public void getDirections(LatLng current, LatLng destination, String mode){
        retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + current.latitude + "," + current.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&mode=" + mode + "&key=" + apiKey;
        Log.d("TAG", "getDirections: " + url);
        retrofitAPI.getDirection(url).enqueue(new Callback<DirectionResponseModel>() {
            @Override
            public void onResponse(Call<DirectionResponseModel> call, Response<DirectionResponseModel> response) {
                Gson gson = new Gson();
                String res = gson.toJson(response.body());
                Log.d("TAG", "onResponse1: " + res);

                if (response.errorBody() == null) {
                    if (response.body() != null) {

                        if (response.body().getDirectionRouteModels().size() > 0) {
                            DirectionRouteModel routeModel = response.body().getDirectionRouteModels().get(0);

                            DirectionLegModel legModel = routeModel.getLegs().get(0);
                            String startAddress = legModel.getStartAddress();
                            String endAddress = legModel.getEndAddress();
                            String time = legModel.getDuration().getText();
                            String distance = legModel.getDistance().getText();
                            List<DirectionStepModel> stepModels = legModel.getSteps();
                            if (directionListener != null) {
                                directionListener.onDirectionReceived(startAddress, endAddress, time, distance, stepModels);
                            }

                            List<LatLng> stepList = new ArrayList<>();

                            PolylineOptions options = new PolylineOptions()
                                    .width(25)
                                    .color(Color.BLUE)
                                    .geodesic(true)
                                    .clickable(true)
                                    .visible(true);

                            List<PatternItem> pattern;
                            if (mode.equals("walking")) {
                                pattern = Arrays.asList(
                                        new Dot(), new Gap(10));

                                options.jointType(JointType.ROUND);
                            } else {
                                pattern = Arrays.asList(
                                        new Dash(30));
                            }

                            options.pattern(pattern);

                            for (DirectionStepModel stepModel : legModel.getSteps()) {
                                List<LatLng> decodedLatLng = decodePoly(stepModel.getPolyline().getPoints());
                                for (LatLng latLng : decodedLatLng) {
                                    stepList.add(latLng);
                                }
                            }

                            options.addAll(stepList);

                            Polyline polyline = googleMap.addPolyline(options);

                            LatLng startLocation = new LatLng(legModel.getStartLocation().getLat(), legModel.getStartLocation().getLng());
                            LatLng endLocation = new LatLng(legModel.getStartLocation().getLat(), legModel.getStartLocation().getLng());

                            LatLng center = new LatLng((startLocation.latitude + endLocation.latitude) / 2, (startLocation.longitude + endLocation.longitude) / 2);
                            float zoomLevel = 16;
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel));


                        } else {
                            Log.d("TAG", "onResponse2: " + response);
                        }
                    } else {
                        Log.d("TAG", "onResponse3: " + response);
                    }
                } else {
                    Log.d("TAG", "onResponse4: " + response);
                }

            }

            @Override
            public void onFailure(Call<DirectionResponseModel> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void getDirections(LatLng origin, LatLng destination) {
        String url = buildDirectionsUrl(origin, destination);
        requestQueue.cancelAll(this);

        JsonObjectRequest request = new JsonObjectRequest(url, null, response -> {
            try {
                List<LatLng> decodedPath = decodePoly(response.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("overview_polyline")
                        .getString("points"));

                if (currentPolyline != null) {
                    currentPolyline.remove();
                }

                currentPolyline = googleMap.addPolyline(new PolylineOptions().addAll(decodedPath));
            } catch (JSONException e) {
                Log.d("DirectionsManager", "getDirections: " + e.getMessage());
                e.printStackTrace();
            }
        }, error -> {
            Log.d("DirectionsManager", "getDirections: " + error.getMessage());
        });

        request.setTag(this);
        requestQueue.add(request);
    }

    private String buildDirectionsUrl(LatLng origin, LatLng destination) {
        return Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
                .buildUpon()
                .appendQueryParameter("origin", origin.latitude + "," + origin.longitude)
                .appendQueryParameter("destination", destination.latitude + "," + destination.longitude)
                .appendQueryParameter("key", apiKey)
                .build().toString();
    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift; shift += 5;
            }
            while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); lat += dlat;

            shift = 0; result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift; shift += 5;
            }
            while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5))); poly.add(p);
        }
        return poly;
    }

    private DirectionListener directionListener;


}
