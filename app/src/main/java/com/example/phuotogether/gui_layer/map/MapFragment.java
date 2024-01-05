package com.example.phuotogether.gui_layer.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.business_layer.map.AllConstant;
import com.example.phuotogether.business_layer.map.DirectionListener;
import com.example.phuotogether.business_layer.map.DirectionsManager;
import com.example.phuotogether.business_layer.map.MapPresenter;
import com.example.phuotogether.business_layer.map.MapPresenterListener;
import com.example.phuotogether.data_access_layer.map.DirectionStepModel;
import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_access_layer.map.MapData;
import com.example.phuotogether.databinding.BottomSheetLocationInfoBinding;
import com.example.phuotogether.databinding.BottomSheetRouteBinding;
import com.example.phuotogether.databinding.FragmentMapBinding;

import com.example.phuotogether.data_access_layer.map.PlaceModel;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapFragment extends Fragment implements MapData.MapDataListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, DirectionListener, MapPresenterListener {
    public static final int TAB_POSITION = 0;
    private FragmentMapBinding binding;
    private BottomSheetLocationInfoBinding bottomSheetLocationInfoBinding;
    private BottomSheetRouteBinding bottomSheetRouteBinding;
    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private MapData mapData;
    private MapPresenter mapPresenter;
    private PlacesClient placesClient;
    private DirectionsManager directionsManager;
    private BottomSheetBehavior<RelativeLayout> bottomSheetBehavior, bottomSheetBehaviorInfoLocation;
    private DirectionStepAdapter adapter;
    private GooglePlaceAdapter googlePlaceAdapter;
    private PlaceModel selectedPlaceModel;
    private boolean isSearching = false, isShowingDirection = false;

    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);

        initializeDependencies();

        setupBottomSheetBehaviors();
        setupStepRecyclerView();
        setupAutoCompleteTextView();
        setupButtons();
        setupNearbySearchUI();

        return binding.getRoot();
    }

    private void initializeDependencies() {
        directionsManager = new DirectionsManager(Volley.newRequestQueue(requireContext()), mMap, this);
        Places.initialize(requireContext(), getString(R.string.place_api_key));
        placesClient = Places.createClient(requireContext());
        mapData = new MapData(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mapData.getLastKnownLocation();
    }

    private void setupBottomSheetBehaviors() {
        bottomSheetRouteBinding = binding.bottomSheet;
        bottomSheetBehavior= BottomSheetBehavior.from(bottomSheetRouteBinding.getRoot());
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetLocationInfoBinding = binding.bottomSheetLocationInfo;
        bottomSheetBehaviorInfoLocation = BottomSheetBehavior.from(bottomSheetLocationInfoBinding.getRoot());
        bottomSheetBehaviorInfoLocation.setHideable(true);
        bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void setupStepRecyclerView() {
        bottomSheetRouteBinding.stepRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DirectionStepAdapter();
        bottomSheetRouteBinding.stepRecyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.placesRecyclerView);
        binding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        googlePlaceAdapter = new GooglePlaceAdapter();
        binding.placesRecyclerView.setAdapter(googlePlaceAdapter);
    }

    private void setupAutoCompleteTextView() {
        binding.inputSearch.setThreshold(1);

        binding.inputSearch.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSuggestion = (String) parent.getItemAtPosition(position);
            binding.inputSearch.setText(selectedSuggestion);
            mapPresenter.clearMap(mMap, getLatLngFromLocation(currentLocation));
            LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
            mapPresenter.performSearch(selectedSuggestion, currentLocation);
        });

        binding.inputSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER
                    || event.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                String query = binding.inputSearch.getText().toString().trim();

                if (!query.isEmpty()) {
                    LatLng currentLocation = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
                    mapPresenter.performSearch(query, currentLocation);
                }
                return true;
            }
            return false;
        });

        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    showIsSearchingUI();
                    mapPresenter.performAutoComplete(s.toString());
                } else {
                    Log.d("MapActivity", "onTextChanged: empty string");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private void setupButtons() {
        binding.btnDelete.setOnClickListener(v -> {
            mapPresenter.clearMap(mMap, getLatLngFromLocation(currentLocation));
            binding.inputSearch.setText("");
            binding.btnDelete.setVisibility(View.GONE);
            binding.btnProfile.setVisibility(View.VISIBLE);
            if (bottomSheetBehaviorInfoLocation.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
            if (binding.placesRecyclerView.getVisibility() == View.VISIBLE) {
                binding.placesRecyclerView.setVisibility(View.GONE);
            }
        });

        binding.btnProfile.setOnClickListener(v -> {
            // Show profile fragment popup
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.show(getChildFragmentManager(), "ProfileFragment");
        });

        binding.btnMyLocation.setOnClickListener(v -> {
            mapData.getLastKnownLocation();
        });
    }

    private void setupNearbySearchUI() {
        for (PlaceModel placeModel : AllConstant.placesName) {
            Chip chip = new Chip(requireContext());
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setText(placeModel.getName());
            chip.setId(placeModel.getId());
            chip.setPadding(8, 8, 8, 8);
            chip.setTextColor(getResources().getColor(R.color.black, null));
            chip.setChipIcon(ResourcesCompat.getDrawable(getResources(), placeModel.getDrawableId(), null));
            chip.setCheckable(true);
            chip.setCheckedIconVisible(false);
            chip.setChipCornerRadius(50);
            binding.placesGroup.addView(chip);
        }

        binding.placesGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != -1) {
                    PlaceModel placeModel = AllConstant.placesName.get(checkedId - 1);
                    binding.inputSearch.setText(placeModel.getName());
                    selectedPlaceModel = placeModel;
                    mapPresenter.clearMap(mMap, getLatLngFromLocation(currentLocation));
                    mapPresenter.performSearchNearby(currentLocation, placeModel.getPlaceType());
                }
            }
        });
    }

    private void showIsSearchingUI() {
        isSearching = true;
//        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_clear, 0);
        binding.btnProfile.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.VISIBLE);
    }


    public void updateSuggestionsUI(List<String> suggestions) {
        // Use requireContext() instead of MapActivity.this
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
        binding.inputSearch.setAdapter(adapter);
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
            mapPresenter = new MapPresenter(this, mMap, this);
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
            mapPresenter.clearMap(mMap, getLatLngFromLocation(currentLocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            Marker selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).snippet("Click here to add a title").draggable(true));
            markerList.add(selectedMarker);
            binding.inputSearch.setText("Dropped pin");
            showIsSearchingUI();

            bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (isShowingDirection) {
                restoreUIComponents();
            }

            bottomSheetLocationInfoBinding.btnShowDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performDirection( getLatLngFromLocation(currentLocation),latLng);
                }
            });
        }
    }

    private void performDirection(LatLng currentLocation, LatLng destination) {
        switchUIFromSearchingToDirecting();
        isShowingDirection = true;
        DirectionsManager directionsManager = new DirectionsManager(Volley.newRequestQueue(requireContext()), mMap, this);
        setupChipGroup(currentLocation, destination, directionsManager);

        showDirectionLayout(currentLocation, destination);
    }

    private void switchUIFromSearchingToDirecting() {
        binding.relLayout1.setVisibility(View.GONE);
        binding.placesGroup.setVisibility(View.GONE);
        binding.directionLayout.setVisibility(View.VISIBLE);
        bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    private void setupChipGroup(LatLng currentLocation, LatLng destination, DirectionsManager directionsManager) {
        directionsManager.getDirections(currentLocation, destination, "driving");
        binding.travelMode.check(R.id.btnChipDriving);

        binding.travelMode.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                handleChipSelection(currentLocation, destination, directionsManager, checkedId);
            }
        });
    }

    private void handleChipSelection(LatLng currentLocation, LatLng destination, DirectionsManager directionsManager, int checkedId) {
        String travelMode = getTravelModeFromChipId(checkedId);
        Log.d("MapFragment", "onCheckedChanged: " + travelMode);

        mapPresenter.clearMap(mMap, currentLocation);
        directionsManager.getDirections(currentLocation, destination, travelMode);
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));
    }

    private String getTravelModeFromChipId(int checkedId) {
        if (checkedId == R.id.btnChipDriving) {
            return "driving";
        } else if (checkedId == R.id.btnChipWalking) {
            return "walking";
        } else if (checkedId == R.id.btnChipBike) {
            return "bicycling";
        } else if (checkedId == R.id.btnChipTrain) {
            return "transit";
        }
        return "driving";
    }

    private void showDirectionLayout(LatLng currentLocation, LatLng destination) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        ImageView backButton = requireActivity().findViewById(R.id.backView);
        backButton.setOnClickListener(v -> {
            isShowingDirection = false;
            restoreUIComponents();
            mapPresenter.clearMap(mMap, currentLocation);
        });

        binding.txtStartLocation.setText(currentLocation.latitude + ", " + currentLocation.longitude);
        binding.txtEndLocation.setText(destination.latitude + ", " + destination.longitude);

        binding.cardLayout.setOnClickListener(v -> {
            // do nothing
        });
    }

    private void restoreUIComponents() {
        binding.inputSearch.setText("");
        binding.directionLayout.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.GONE);
        binding.btnProfile.setVisibility(View.VISIBLE);

        binding.relLayout1.setVisibility(View.VISIBLE);
        binding.placesGroup.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    private LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onDirectionReceived(String startLocation1, String endLocation1,
                                    String time, String distance, List<DirectionStepModel> steps) {
        binding.txtStartLocation.setText(startLocation1);
        binding.txtEndLocation.setText(endLocation1);
        bottomSheetRouteBinding.txtSheetTime.setText(time);
        bottomSheetRouteBinding.txtSheetDistance.setText(distance);
        adapter.setDirectionStepModels(steps);
    }

    @Override
    public void onShowDirectionClicked(LatLng startLocation, LatLng endLocation) {
        Log.d("MapFragment", "onShowDirectionClicked: ");
        performDirection(startLocation, endLocation);
    }
    @Override
    public void onNearbyPlacesFetch(List<GooglePlaceModel> googlePlaceModels) {
        binding.placesRecyclerView.setVisibility(View.VISIBLE);

        List<GooglePlaceModel> googlePlaceModelList = new ArrayList<>();
        googlePlaceModelList.addAll(googlePlaceModels);
        googlePlaceAdapter.setGooglePlaceModels(googlePlaceModelList);
        googlePlaceAdapter.notifyDataSetChanged();

        binding.placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (position > -1) {
                    GooglePlaceModel googlePlaceModel = googlePlaceModelList.get(position);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(googlePlaceModel.getGeometry().getLocation().getLat(),
                            googlePlaceModel.getGeometry().getLocation().getLng()), 15));
                }
            }
        });
    }
}