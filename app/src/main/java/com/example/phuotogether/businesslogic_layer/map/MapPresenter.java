package com.example.phuotogether.businesslogic_layer.map;



import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.map.GoogleResponseModel;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPresenter {
    private final MapFragment mMapFragment;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private Marker marker;
    private GoogleMap mMap;
    private DirectionsManager directionsManager;
    private int radius = 5000;
    private List<GooglePlaceModel> googlePlaceModelList;
    private MapPresenterListener mapPresenterListener;
    public MapPresenter(MapFragment mapFragment, GoogleMap mMap, MapPresenterListener mapPresenterListener) {
        this.mMapFragment = mapFragment;
        this.mMap = mMap;
        this.mapPresenterListener = mapPresenterListener;
        directionsManager = new DirectionsManager(Volley.newRequestQueue(mMapFragment.requireContext()), mMap, mapFragment);
    }

    public void setMap(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    public void performSearch(String selectedSuggestion, LatLng currentLocation, boolean found) {
        // Search for the selected suggestion
        clearMap(mMap, currentLocation);
        Geocoder geocoder = new Geocoder(mMapFragment.requireContext());
        List<Address> addressList = new ArrayList<>();

        try {
            addressList = geocoder.getFromLocationName(selectedSuggestion, 1);
        } catch (IOException e) {
            Log.d("MapActivity", "performSearch: " + e.getMessage());
        }
        if (addressList.size() > 0) {
            found = true;
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mMapFragment.requireActivity().findViewById(R.id.bottom_sheet_location_info));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            Button showDirectionsButton = mMapFragment.requireActivity().findViewById(R.id.btnShowDirection);
            showDirectionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MapActivity", "onClick: " + currentLocation + " " + latLng);
                    clearMap(mMap, currentLocation);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(selectedSuggestion));
                    mapPresenterListener.onShowDirectionClicked(currentLocation, latLng);
                }
            });

            moveCamera(latLng, 15, selectedSuggestion);
        }
        else {
            found = false;
            Log.d("MapActivity", "performSearch: addressList is empty");
        }
    }
    public void clearMap(GoogleMap mMap, LatLng currentLatLng) {
        mMap.clear();
        markerList.clear();

        // add marker for current location again
        if (currentLatLng != null) {
            marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot_pic)).position(currentLatLng).draggable(true)
                    .title("My Location"));
            marker.setTitle("My Location");
            markerList.add(marker);
        }
    }
    public void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (marker != null) {
            Log.d("blue dot not null", "");
            marker.remove();
        }
        if (!title.equals("My Location")) {
            Log.d("blue dot not my location", "");
            marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                    .title(title));
            marker.setTitle(title);
            markerList.add(marker);
        }
        else {
            Log.d("blue dot my location", "");
            marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot_pic)).position(latLng).draggable(true)
                    .title(title));
            marker.setTitle(title);
            markerList.add(marker);
        }
    }

    public void performAutoComplete(String query) {
        Log.d("MapActivity", "performAutoComplete: " + query);
        PlacesClient placesClient = Places.createClient(mMapFragment.getActivity());

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
//                .setCountry("YOUR_COUNTRY_CODE")  // Set to a specific country if needed
                .build();

        placesClient.findAutocompletePredictions(request)
                .addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictions = task.getResult();
                            if (predictions != null) {
                                List<AutocompletePrediction> predictionList = predictions.getAutocompletePredictions();

                                // Create a list of suggestion strings
                                List<String> suggestions = new ArrayList<>();
                                for (AutocompletePrediction prediction : predictionList) {
                                    suggestions.add(prediction.getFullText(null).toString());
                                }
                                // Update your UI with the suggestions
                                mMapFragment.updateSuggestionsUI(suggestions);
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception != null) {
                                Log.e("MapActivity", "Auto complete prediction failed with exception: ", exception);
                            } else {
                                Log.d("MapActivity", "Auto complete prediction unsuccessful with no exception");
                            }
                        }
                    }
                    catch (Exception e) {
                        Log.d("MapActivity", "Auto complete prediction exception " + e.getMessage());
                    }
                });
    }

    public void performSearchNearby(Location currentLocation, String placeName) {
        googlePlaceModelList = new ArrayList<>();
        RetrofitAPI retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + currentLocation.getLatitude() + "," + currentLocation.getLongitude()
                + "&radius=" + radius + "&type=" + placeName + "&key=" + mMapFragment.getResources().getString(R.string.place_api_key);
        Log.d("TAG", "performSearchNearby: " + url);
        if (currentLocation != null) {
            retrofitAPI.getNearByPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<GoogleResponseModel> call, @NonNull Response<GoogleResponseModel> response) {
                    Gson gson = new Gson();
                    String res = gson.toJson(response.body());
                    Log.d("TAG", "onResponse: " + res);
                    if (response.errorBody() == null) {
                        if (response.body() != null) {
                            if (response.body().getGooglePlaceModelList() != null && response.body().getGooglePlaceModelList().size() > 0) {

                                googlePlaceModelList.clear();
                                mMap.clear();
                                for (int i = 0; i < response.body().getGooglePlaceModelList().size(); i++) {

//                                    if (userSavedLocationId.contains(response.body().getGooglePlaceModelList().get(i).getPlaceId())) {
//                                        response.body().getGooglePlaceModelList().get(i).setSaved(true);
//                                    }
                                    googlePlaceModelList.add(response.body().getGooglePlaceModelList().get(i));
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(response.body().getGooglePlaceModelList().get(i).getGeometry().getLocation().getLat(),
                                                    response.body().getGooglePlaceModelList().get(i).getGeometry().getLocation().getLng()))
                                            .title(response.body().getGooglePlaceModelList().get(i).getName())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                                mapPresenterListener.onNearbyPlacesFetch(googlePlaceModelList);

                            } else if (response.body().getError() != null) {
                                Toast.makeText(mMapFragment.requireContext(), "Error : " + response.body().getError(), Toast.LENGTH_SHORT).show();
                            } else {

                                mMap.clear();
                                googlePlaceModelList.clear();
                                mapPresenterListener.onNearbyPlacesFetch(googlePlaceModelList);
                                radius += 1000;
                                Log.d("TAG", "onResponse: " + radius);
                                performSearchNearby(currentLocation, placeName);

                            }
                        }

                    } else {
                        Log.d("TAG", "onResponse: " + response.errorBody());
                        Toast.makeText(mMapFragment.requireContext(), "Error : " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<GoogleResponseModel> call, Throwable t) {

                    Log.d("TAG", "onFailure: " + t);

                }
            });
        }
    }

}
