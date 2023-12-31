package com.example.phuotogether.gui_layer.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.business_layer.map.DirectionsManager;
import com.example.phuotogether.business_layer.map.MapPresenter;
import com.example.phuotogether.data_access_layer.map.MapData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapFragment extends Fragment implements MapData.MapDataListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {
    public static final int TAB_POSITION = 0;

    private final int FINE_LOCATION_CODE = 1;
    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private AutoCompleteTextView autoCompleteTextView;
    private Marker marker;
    private Marker selectedMarker = null;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private FloatingActionButton myLocationButton;
    private MapData mapData;
    private MapPresenter mapPresenter;
    private PlacesClient placesClient;
    private DirectionsManager directionsManager;
    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        if (getActivity() != null) {
            // initialize
            Places.initialize(requireContext(), getString(R.string.place_api_key));
            placesClient = Places.createClient(requireContext());
            mapData = new MapData(this);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            mapData.getLastKnownLocation();

            // bind views
            autoCompleteTextView = rootView.findViewById(R.id.input_search);
            myLocationButton = rootView.findViewById(R.id.btn_my_location);

            autoCompleteTextView.setThreshold(1); // Start suggestions after typing 1 character

            autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedSuggestion = (String) parent.getItemAtPosition(position);
                autoCompleteTextView.setText(selectedSuggestion);
                mapPresenter.clearMap(mMap, currentLocation);
                LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
                mapPresenter.performSearch(selectedSuggestion, currentLocation);
            });
            autoCompleteTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER
                        || event.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    String query = autoCompleteTextView.getText().toString().trim();

                    if (!query.isEmpty()) {
                        LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
                        mapPresenter.performSearch(query, currentLocation);

                    }
                    return true;
                }
                return false;
            });
            autoCompleteTextView.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        Log.d("MapActivity", "onTextChanged: empty string");
                        // Perform autocomplete predictions
                        mapPresenter.performAutoComplete(s.toString());
                    }
                    else {
                        Log.d("MapActivity", "onTextChanged: empty string");
                    }
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {
                    // Do nothing
                }
            });

            autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mapPresenter.clearMap(mMap, currentLocation);
                    final int DRAWABLE_RIGHT = 2;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (autoCompleteTextView.getRight() - autoCompleteTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            autoCompleteTextView.setText("");
                            if (marker != null) {
                                marker.remove();
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });


            LinearLayout buttonContainer = rootView.findViewById(R.id.buttonContainer);
            List<String> spotCategories = Arrays.asList("Restaurant", "Coffee", "Shopping", "Hotels", "Gas", "Hospitals & clinics");

            for (String category : spotCategories) {
                TextView button = new TextView(requireContext());
                button.setText(category);
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                button.setPadding(50, 10, 50, 10);
                button.setElevation(5);
                // Set layout params
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(10, 10, 10, 10);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.rounded_button);
                buttonContainer.addView(button);
                button.setOnClickListener(v -> {
                    mapPresenter.performNearbySearch(category,placesClient,currentLocation);
                });
            }

            myLocationButton.setOnClickListener(v -> {
                mapData.getLastKnownLocation();
            });

        }

        return rootView;
    }


    public void updateSuggestionsUI(List<String> suggestions) {
        // Use requireContext() instead of MapActivity.this
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);
    }

    @Override
    public void onLastLocationReceived(Location location) {
        currentLocation = location;
        SupportMapFragment ggMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (ggMapFragment != null) {
            ggMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (currentLocation != null) {
            // Move camera and other map-related logic
            mapPresenter = new MapPresenter(this, mMap);
            mapPresenter.moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15, "My Location");
        }

        // Set up UI settings and listeners
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Handle permission results
        if (requestCode == MapData.FINE_LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapData.getLastKnownLocation();
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (mMap != null) {
            // Your existing logic for clearing the map and adding a marker
            mapPresenter.clearMap(mMap, currentLocation);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            Marker selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).snippet("Click here to add a title").draggable(true));
            markerList.add(selectedMarker);

            // Show an info window for the marker
            DirectionsManager directionsManager = new DirectionsManager(Volley.newRequestQueue(requireContext()), mMap);
            LocationInfoFragment locationInfoFragment = LocationInfoFragment.newInstance(
                    latLng,
                    getLatLngFromLocation(currentLocation),
                    directionsManager
            );

            // Use getChildFragmentManager() instead of getSupportFragmentManager()
            locationInfoFragment.show(getChildFragmentManager(), "LocationInfoFragment");
        }
    }

    private LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}