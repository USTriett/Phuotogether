package com.example.phuotogether.businesslogic_layer.map;


import android.graphics.Color;
import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.map.DirectionLegModel;
import com.example.phuotogether.data_layer.map.DirectionResponseModel;
import com.example.phuotogether.data_layer.map.DirectionRouteModel;
import com.example.phuotogether.data_layer.map.DirectionStepModel;
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
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DirectionsManager {

    private RequestQueue requestQueue;
    private GoogleMap googleMap;
    private String apiKey = "AIzaSyCl2PBKLn0jILonkq3kLx7Qw8CSwgUW6_o";
    private Polyline currentPolyline;
    private RetrofitAPI retrofitAPI;
    private DirectionListener directionListener;
    public DirectionsManager(RequestQueue requestQueue, GoogleMap googleMap, DirectionListener listener) {
        this.requestQueue = requestQueue;
        this.googleMap = googleMap;
        this.directionListener = listener;
    }
    private GoogleMapOptions googleMapOptions = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .zoomControlsEnabled(true);

    //download offline map caching memory of android device
    public void CachingOfflineMap(MapFragment mapFragment, LatLng topLeft, LatLng bottomRight) {
        FragmentTransaction fragmentTransaction = mapFragment.getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Cấu hình bản đồ
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                if (ActivityCompat.checkSelfPermission(mapFragment.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mapFragment.getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // Tải xuống dữ liệu bản đồ cho vùng cụ thể
                LatLngBounds mapBounds = new LatLngBounds(topLeft, bottomRight);
                int minZoom = 12;
                int maxZoom = 16;
                googleMap.setLatLngBoundsForCameraTarget(mapBounds);
                googleMap.setMinZoomPreference(minZoom);
                googleMap.setMaxZoomPreference(maxZoom);

                // Bắt đầu tải dữ liệu bản đồ cho vùng cụ thể
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        // Bản đồ đã được tải xuống hoàn tất
                        // Bạn có thể sử dụng bản đồ trong chế độ offline ở đây
                        //dosthg
                    }
                });
            }
        });
    }



    public void getDirections(LatLng current, LatLng destination, String mode){
        retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + current.latitude + "," + current.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&mode=" + mode + "&language=vi&key=" + apiKey;
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



}
