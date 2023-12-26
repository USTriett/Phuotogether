package com.example.phuotogether.business_layer.map;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.gui_layer.map.LocationInfoFragment;
import com.example.phuotogether.gui_layer.map.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapPresenter {
    private final MapFragment mMapFragment;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;
    private Marker marker;
    private final GoogleMap mMap;
    private DirectionsManager directionsManager;
    public MapPresenter(MapFragment mapFragment, GoogleMap mMap) {
        this.mMapFragment = mapFragment;
        this.mMap = mMap;
        directionsManager = new DirectionsManager(Volley.newRequestQueue(mMapFragment.requireContext()), mMap);
    }

    public void performSearch(String selectedSuggestion, LatLng currentLocation) {
        // Search for the selected suggestion
        Geocoder geocoder = new Geocoder(mMapFragment.requireContext());
        List<Address> addressList = new ArrayList<>();

        try {
            addressList = geocoder.getFromLocationName(selectedSuggestion, 1);
        } catch (IOException e) {
            Log.d("MapActivity", "performSearch: " + e.getMessage());
        }
        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            LocationInfoFragment locationInfoFragment = LocationInfoFragment.newInstance(latLng,currentLocation,directionsManager);
            locationInfoFragment.show(mMapFragment.requireActivity().getSupportFragmentManager(), "locationInfoFragment");
            moveCamera(latLng, 15, selectedSuggestion);
        }
        else {
            Log.d("MapActivity", "performSearch: addressList is empty");
        }


    }
    public void clearMap(GoogleMap mMap, Location lastKnownLocation) {
        mMap.clear();
        markerList.clear();

        // add marker for current location again
        if (lastKnownLocation != null) {
            Log.d("MapActivity", "onLastLocationReceived: " + lastKnownLocation.toString());
            LatLng currentLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot_pic)).position(currentLatLng).draggable(true)
                    .title("My Location"));
            marker.setTitle("My Location");
            markerList.add(marker);
        }
    }
    public void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            if (marker != null) {
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                    .title(title));
            marker.setTitle(title);
            markerList.add(marker);
        }
        else {
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

    public void performNearbySearch(String categoryText, PlacesClient placesClient, Location currentLocation) {
        LocationBias locationBias = RectangularBounds.newInstance(
                new LatLng(currentLocation.getLatitude() - 0.05, currentLocation.getLongitude() - 0.05),
                new LatLng(currentLocation.getLatitude() + 0.05, currentLocation.getLongitude() + 0.05)
        );
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setQuery(categoryText)
                .setLocationBias(locationBias)
                .build();
        placesClient.findAutocompletePredictions(request)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FindAutocompletePredictionsResponse response = task.getResult();
                        if (response != null && response.getAutocompletePredictions() != null) {
                            List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
                            for (AutocompletePrediction prediction : predictions) {
                                String placeId = prediction.getPlaceId();
                                String placeName = prediction.getPrimaryText(null).toString();
                                fetchPlaceDetails(placeId, placeName, placesClient);
                            }
                        }
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("MapActivity", "Autocomplete search failed with exception: ", exception);
                        }
                    }
                });
    }

    public void fetchPlaceDetails(String placeId, String placeName, PlacesClient placesClient) {
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, Arrays.asList(Place.Field.LAT_LNG))
                .build();

        placesClient.fetchPlace(request)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Place place = task.getResult().getPlace();
                        LatLng placeLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                        mMap.addMarker(new MarkerOptions()
                                .position(placeLatLng)
                                .title(placeName));

                        // You can perform other actions here based on the nearby places
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("MapActivity", "Fetch place details failed with exception: ", exception);
                        }
                    }
                });
    }


}
