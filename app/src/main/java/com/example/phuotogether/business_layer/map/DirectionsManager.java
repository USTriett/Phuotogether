package com.example.phuotogether.business_layer.map;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phuotogether.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DirectionsManager {

    private RequestQueue requestQueue;
    private GoogleMap googleMap;
    private String apiKey = "AIzaSyBK-ChfzUw0sqUDr3VvB8UzN2RzLM6hSUM";
    private Polyline currentPolyline;

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


    public DirectionsManager(RequestQueue requestQueue, GoogleMap googleMap) {
        this.requestQueue = requestQueue;
        this.googleMap = googleMap;
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
}
