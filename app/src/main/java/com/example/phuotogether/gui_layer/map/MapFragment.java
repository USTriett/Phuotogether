package com.example.phuotogether.gui_layer.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.toolbox.Volley;
import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.map.AllConstant;
import com.example.phuotogether.businesslogic_layer.map.DirectionListener;
import com.example.phuotogether.businesslogic_layer.map.DirectionsManager;
import com.example.phuotogether.businesslogic_layer.map.MapPresenter;
import com.example.phuotogether.businesslogic_layer.map.MapPresenterListener;
import com.example.phuotogether.data_layer.map.DirectionStepModel;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.map.MapData;
import com.example.phuotogether.data_layer.map.PlaceModel;
import com.example.phuotogether.databinding.BottomSheetLocationInfoBinding;
import com.example.phuotogether.databinding.BottomSheetRouteBinding;
import com.example.phuotogether.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements MapData.MapDataListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, DirectionListener, LocationListener, MapPresenterListener, TextToSpeech.OnInitListener {
    public static final int TAB_POSITION = 0;
    private FragmentMapBinding binding;
    private BottomSheetLocationInfoBinding bottomSheetLocationInfoBinding;
    private BottomSheetRouteBinding bottomSheetRouteBinding;
    private GoogleMap mMap;
    Location currentLocation;
    LatLng pinnedLatLng;
    FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private MapData mapData;
    private MapPresenter mapPresenter;
    private PlacesClient placesClient;
    private DirectionsManager directionsManager;
    private MapView mapView;

    private BottomSheetBehavior<RelativeLayout> bottomSheetBehavior, bottomSheetBehaviorInfoLocation;
    private DirectionStepAdapter adapter;
    private GooglePlaceAdapter googlePlaceAdapter;
    private PlaceModel selectedPlaceModel;
    private boolean isSearching = false, isShowingDirection = false;

    // for text to speech
    private TextToSpeech textToSpeech;
    private Context context;
    private List<DirectionStepModel> stepList = new ArrayList<>();
    private float[] prevDistanceToEnd = {0.0f};



    private LocationManager locationManager;
    final float ZOOM = 15;
    public static Fragment newInstance() {
        return new MapFragment();
    }
    private boolean isTrackingLocation = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);

        initializeDependencies();

            // bind views
//        autoCompleteTextView = rootView.findViewById(R.id.input_search);
//        myLocationButton = rootView.findViewById(R.id.btn_my_location);
//        deleteButton = rootView.findViewById(R.id.btn_delete);
//        profileButton = rootView.findViewById(R.id.btn_profile);
//        assetManager = getContext().getAssets();
//        autoCompleteTextView.setThreshold(1); // Start suggestions after typing 1 character
        setupBottomSheetBehaviors();
        setupStepRecyclerView();
        setupAutoCompleteTextView();
        setupButtons();
        setupNearbySearchUI();
        setupFloatingComponents();

        startLocationUpdates();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLocationUpdates();
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
            LatLng currentLatLng = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
            mapPresenter.performSearch(selectedSuggestion, currentLatLng, true);
        });

        binding.inputSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER
                    || event.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                String query = binding.inputSearch.getText().toString().trim();

                if (!query.isEmpty()) {
                    LatLng currentLatLng = new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude());
                    boolean foundLocation = false;
                    mapPresenter.performSearch(query, currentLatLng, foundLocation);
                    if (!foundLocation) {
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
                    }
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

            // bấm nút xóa thì pinnedLatLng trả về là current location
            pinnedLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pinnedLatLng, 15));

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
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.show(getChildFragmentManager(), "ProfileFragment");
            TextView downloadOfflineMap = profileFragment.getDownloadOfflineMapTextView();
            // Get the current visible region and camera position
            VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
            CameraPosition cameraPosition = mMap.getCameraPosition();

// Get the bounds of the visible region
            LatLngBounds latLngBounds = visibleRegion.latLngBounds;

// Get the screen coordinates of the bounds
            Point southwestPoint = mMap.getProjection().toScreenLocation(latLngBounds.southwest);
            Point northeastPoint = mMap.getProjection().toScreenLocation(latLngBounds.northeast);

// Calculate the width and height of the visible map area in pixels
            int mapWidth = northeastPoint.x - southwestPoint.x;
            int mapHeight = northeastPoint.y - southwestPoint.y;

// Define the zoom level for the tiles
            int zoomLevel = (int) cameraPosition.zoom;

// Calculate the tile coordinates
            int tileXStart = (int) (latLngBounds.southwest.longitude * Math.pow(2, zoomLevel) / 256);
            int tileYStart = (int) (latLngBounds.northeast.latitude * Math.pow(2, zoomLevel) / 256);
            int tileXEnd = (int) (latLngBounds.northeast.longitude * Math.pow(2, zoomLevel) / 256);
            int tileYEnd = (int) (latLngBounds.southwest.latitude * Math.pow(2, zoomLevel) / 256);

        });

        binding.btnMyLocation.setOnClickListener(v -> {
            mapData.getLastKnownLocation();
        });    }

    private void setupNearbySearchUI() {
        for (PlaceModel placeModel : AllConstant.placesName) {
//            Context styledContext = new ContextThemeWrapper(requireContext(), R.style.CustomChipPlaces);
//            Chip chip = new Chip(styledContext);
            Chip chip = new Chip(requireContext());
            //chip.setChipBackgroundColorResource(R.color.white);
            chip.setText(placeModel.getName());
            chip.setId(placeModel.getId());
            chip.setPadding(8, 8, 8, 8);
            //chip.setTextColor(getResources().getColor(R.color.black, null));
            chip.setChipIcon(ResourcesCompat.getDrawable(getResources(), placeModel.getDrawableId(), null));
            chip.setCheckable(true);
            chip.setCheckedIconVisible(false);
            binding.placesGroup.addView(chip);
        }

        binding.placesGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != -1) {
                    PlaceModel placeModel = AllConstant.placesName.get(checkedId - 1);
                    binding.inputSearch.setText(placeModel.getName());
                    selectedPlaceModel = placeModel;

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pinnedLatLng, 15));

                    mapPresenter.clearMap(mMap, getLatLngFromLocation(currentLocation));
                    mapPresenter.performSearchNearby(pinnedLatLng, placeModel.getPlaceType());
                }
            }
        });
    }
    private AssetManager assetManager;
    private void saveMapImageToAssets(Bitmap snapshot) {
        OutputStream out = null;

        try {
            out = assetManager.openFd("map_image.png").createOutputStream();
            snapshot.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


   private void setupFloatingComponents() {
        binding.floatingInstruction.setVisibility(View.GONE);
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
        pinnedLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        SupportMapFragment ggMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (ggMapFragment != null) {
            ggMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("MapFragment", "onMapReady: ");
        // Set up map
        mMap = googleMap;

        // Move camera and other map-related logic
        if (currentLocation != null) {
            if (mapPresenter == null) {
                Log.d("MapFragment", "onMapReady: mapPresenter is null");
                mapPresenter = new MapPresenter(this, mMap, this);
                mapPresenter.moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15, "My Location (button clicked)");
            }
            else {
                Log.d("MapFragment", "onMapReady: mapPresenter is not null");
                mapPresenter.setMap(mMap);          // khi nhấn nt btnMyLocation thì có tạo instance googleMap mới -> phải set ở đây
                mapPresenter.moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15, "My Location (button clicked)");
            }
        }
        else {
            Log.d("MapFragment", "onMapReady: currentLocation is null");
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
            pinnedLatLng = latLng;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pinnedLatLng, 15));
            Marker selectedMarker = mMap.addMarker(new MarkerOptions().position(pinnedLatLng).snippet("Click here to add a title").draggable(true));
            markerList.add(selectedMarker);
            binding.inputSearch.setText(pinnedLatLng.latitude + ", " + pinnedLatLng.longitude);
            showIsSearchingUI();

            bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (isShowingDirection) {
                restoreUIComponents();
            }

            bottomSheetLocationInfoBinding.btnShowDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("onMapClick", pinnedLatLng.latitude + ", " + pinnedLatLng.longitude);
                    performDirection(getLatLngFromLocation(currentLocation), pinnedLatLng);
                }
            });
        }
    }
    
    private void performDirection(LatLng currentLatLng, LatLng destinationLatLng) {
        switchUIFromSearchingToDirecting();
        isShowingDirection = true;
        if (directionsManager == null)
            directionsManager = new DirectionsManager(Volley.newRequestQueue(requireContext()), mMap, this);
        else
            directionsManager.setMap(mMap);
        setupChipGroup(currentLatLng, destinationLatLng, directionsManager);

        showDirectionLayout(currentLatLng, destinationLatLng);
    }

    private void switchUIFromSearchingToDirecting() {
        binding.relLayout1.setVisibility(View.GONE);
        binding.placesGroup.setVisibility(View.GONE);
        binding.directionLayout.setVisibility(View.VISIBLE);
        bottomSheetBehaviorInfoLocation.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    private void setupChipGroup(LatLng currentLatLng, LatLng destinationLatLng, DirectionsManager directionsManager) {
        Log.d("setupChipGroup", destinationLatLng.toString());
        directionsManager.getDirections(currentLatLng, destinationLatLng, "driving");
        //mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("DestinationX"));
        binding.travelMode.check(R.id.btnChipDriving);

        binding.travelMode.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                Log.d("performDirection", "");
                handleChipSelection(currentLatLng, destinationLatLng, directionsManager, checkedId);
            }
        });
    }

    private void handleChipSelection(LatLng currentLatLng, LatLng destinationLatLng, DirectionsManager directionsManager, int checkedId) {
        String travelMode = getTravelModeFromChipId(checkedId);
        Log.d("MapFragment", "onCheckedChanged: " + travelMode);

        mapPresenter.clearMap(mMap, currentLatLng);
        directionsManager.getDirections(currentLatLng, destinationLatLng, travelMode);
        mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("DestinationX"));
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

    private void showDirectionLayout(LatLng currentLatLng, LatLng destinationLatLng) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        ImageView backButton = requireActivity().findViewById(R.id.backView);
        backButton.setOnClickListener(v -> {
            isShowingDirection = false;
            restoreUIComponents();
            mapPresenter.clearMap(mMap, currentLatLng);
        });

        binding.txtStartLocation.setText(currentLatLng.latitude + ", " + currentLatLng.longitude);
        binding.txtEndLocation.setText(destinationLatLng.latitude + ", " + destinationLatLng.longitude);

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
        binding.floatingInstruction.setVisibility(View.GONE);
    }

    private LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onDirectionReceived(String startLocation1, String endLocation1,
                                    String time, String distance, List<DirectionStepModel> steps) {

        adapter.setDirectionStepModels(steps);
        stepList = steps;
        binding.txtStartLocation.setText(startLocation1);
        binding.txtEndLocation.setText(endLocation1);
        Log.d("txtEndLocation", endLocation1);
        bottomSheetRouteBinding.txtSheetTime.setText(time);
        bottomSheetRouteBinding.txtSheetDistance.setText(distance);
        textToSpeech.speak("Đây là hướng dẫn chỉ đường đến " + getSpokenInstruction(endLocation1), TextToSpeech.QUEUE_FLUSH, null, null);


        for (int i = 0; i < stepList.size(); i++){
            String instruction = stepList.get(i).getHtmlInstructions();
            Log.d("test step " + String.valueOf(i), instruction);
            Log.d("test step " + String.valueOf(i), getDisplayedInstruction(instruction));
            Log.d("test step " + String.valueOf(i), getSpokenInstruction(instruction));
        }

        bottomSheetRouteBinding.btnStartInstruction.setOnClickListener(v -> {
            String rawInstruction = steps.get(0).getHtmlInstructions();
            String displayedInstruction = getDisplayedInstruction(rawInstruction);
            String spokenInstruction = getSpokenInstruction(rawInstruction);
            Log.d("First location is", displayedInstruction);
            textToSpeech.speak(spokenInstruction, TextToSpeech.QUEUE_FLUSH, null, null);

            binding.directionLayout.setVisibility(View.GONE);
            binding.floatingInstruction.setVisibility(View.VISIBLE);
            binding.floatingInstructionText.setText(displayedInstruction);

            isTrackingLocation = true;                         // track user's location
        });


        bottomSheetRouteBinding.btnStopInstruction.setOnClickListener(v -> {
            binding.floatingInstruction.setVisibility(View.GONE);
            binding.directionLayout.setVisibility(View.VISIBLE);

            isTrackingLocation = false;                          // stop tracking user's location
        });
    }

    private void startLocationUpdates() {
        try {
            // Check location permissions
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Register for location updates
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
            } else {
                // Handle the case where permissions are not granted
                // You should request permissions here or handle it according to your app's logic
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        // Unregister the LocationListener to stop location updates
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MapFragment", "onLocationChanged: ");
        if (mapPresenter == null)
            mapPresenter = new MapPresenter(this, mMap, this);
        // update user's location on the map
        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mapPresenter.moveCamera(userLatLng, 15, "My Location (location changed)");
        // Toast.makeText(requireContext(), "Location changed.", Toast.LENGTH_SHORT).show();

        // check number of steps
        int stepListLength = stepList.size();
        if (stepListLength == 0)
            return;

        // check if the user's location is within any step's bounds
        for (int i = 0; i < stepListLength; i++) {
            DirectionStepModel currentStep = stepList.get(i);
            if (currentStep.isUserAboutToEndStep(userLatLng, prevDistanceToEnd)) {
                if (i < stepListLength - 1){
                    // get instruction
                    DirectionStepModel nextStep = stepList.get(i + 1);
                    String rawInstruction = nextStep.getHtmlInstructions();
                    String displayedInstruction = getDisplayedInstruction(rawInstruction);
                    String spokenInstruction = getSpokenInstruction(rawInstruction);

                    // update instruction on the map
                    binding.floatingInstructionText.setText(displayedInstruction);
                    if (!textToSpeech.isSpeaking()) textToSpeech.speak(spokenInstruction, TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
                }
                else {
                    String arrivedInstruction = "You have arrived at the destination.";
                    binding.floatingInstructionText.setText(arrivedInstruction);
                    if (!textToSpeech.isSpeaking()) textToSpeech.speak(arrivedInstruction, TextToSpeech.QUEUE_FLUSH, null, null);
                    stepList.clear();
                }
            }
        }
    }

    public static String getDisplayedInstruction(String rawInstruction){
        String displayedInstruction = rawInstruction
                .replace("Đ.", "Đường")
                .replaceAll("<div style=\"[^\"]*\">", ". ")           // div này sử dụng cho kết thúc câu
                .replaceAll("<.*?>", "");
        return displayedInstruction;
    }

    public static String getSpokenInstruction(String rawInstruction){
        String spokenInstruction = rawInstruction
                .replace("Đ.", "Đường")
                .replaceAll("<div style=\"[^\"]*\">", ". ")           // div này sử dụng cho kết thúc câu
                .replaceAll("<.*?>", "")
                .replaceAll("/", " xuyệt ");
        return spokenInstruction;
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
                    try{
                        GooglePlaceModel googlePlaceModel = googlePlaceModelList.get(position);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(googlePlaceModel.getGeometry().getLocation().getLat(), googlePlaceModel.getGeometry().getLocation().getLng()), 15));
                    }
                    catch (Exception e){

                    }
                }
            }
        });
    }

    // for text to speech
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        textToSpeech = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for speech synthesis.
            int result = textToSpeech.setLanguage(Locale.getDefault());

            // Check if the language is supported.
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
            } else {
                // Text-to-Speech is ready. You can now use it to speak.
                // textToSpeech.speak("Hello, welcome to your app!", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        } else {
            Toast.makeText(context, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    // Be sure to release the TextToSpeech resources in the fragment's onDestroy method.
    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        // Stop location updates
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
        super.onDestroy();
    }
}