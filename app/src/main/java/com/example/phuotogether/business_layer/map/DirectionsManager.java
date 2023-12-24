package com.example.phuotogether.business_layer.map;



import static android.provider.Settings.System.getString;

import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phuotogether.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DirectionsManager {

    private RequestQueue requestQueue;
    private GoogleMap googleMap;
    private String apiKey = "AIzaSyAwBoi-ZrQxdKNnaPa8SMBT2ip4ik5Fn0g";
    private Polyline currentPolyline;

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
}
