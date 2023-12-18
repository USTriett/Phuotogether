package com.example.phuotogether.gui_layer.map;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // initialize
        Places.initialize(getApplicationContext(), getString(R.string.place_api_key));
        placesClient = Places.createClient(this);
        mapData = new MapData(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapData.getLastKnownLocation();

        // bind views
        autoCompleteTextView = findViewById(R.id.input_search);
        myLocationButton = findViewById(R.id.btn_my_location);

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


        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);
        List<String> spotCategories = Arrays.asList("Restaurant", "Coffee", "Shopping", "Hotels", "Gas", "Hospitals & clinics");

        for (String category : spotCategories) {
            TextView button = new TextView(this);
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

    public void updateSuggestionsUI(List<String> suggestions) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);
    }

    public void onLastLocationReceived(Location location) {
        currentLocation = location;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mapPresenter = new MapPresenter(this, mMap);
        mapPresenter.moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15, "My Location");

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapData.getLastKnownLocation();
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mapPresenter.clearMap(mMap, currentLocation);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).snippet("Click here to add a title").draggable(true));
        markerList.add(selectedMarker);
        // Show an info window for the marker
        directionsManager = new DirectionsManager(Volley.newRequestQueue(this), mMap);
        LocationInfoFragment locationInfoFragment = LocationInfoFragment.newInstance(latLng,getLatLngFromLocation(currentLocation),directionsManager);
        locationInfoFragment.show(getSupportFragmentManager(), "LocationInfoFragment");
    }

    private LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

}