package com.example.phuotogether.gui_layer.trip.destinationList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;


import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_access_layer.map.GoogleResponseModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDestinationActivity extends AppCompatActivity {
    private RecyclerView rvPopularPlaces;
    private Trip selectedTrip;
    private String[] cursorColumns = {"_id", "name"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);

        selectedTrip = (Trip) getIntent().getSerializableExtra("trip");
        Log.d("SearchPlacesActivity", "onCreate: " + selectedTrip.getArrivalPlace());

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete_search);

        ArrayList<GooglePlaceModel> popularPlaces = new ArrayList<>();
        fetchPopularPlaces(new OnDataFetchedListener() {
            @Override
            public void onDataFetched(ArrayList<GooglePlaceModel> data) {
                // Xử lý dữ liệu khi nó đã được trả về
                popularPlaces.addAll(data);
                Log.d("SearchPlacesActivity", "onDataFetched: " + popularPlaces.size());

                // Tạo và đặt Adapter cho AutoCompleteTextView
                autoCompleteTextView.setThreshold(1);
                PlaceAutoCompleteAdapter adapter = new PlaceAutoCompleteAdapter(SearchDestinationActivity.this, android.R.layout.simple_dropdown_item_1line, popularPlaces);
                autoCompleteTextView.setAdapter(adapter);


                // Bắt sự kiện khi dữ liệu thay đổi
                autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Cập nhật dữ liệu khi người dùng nhập
                        Log.d("SearchPlacesActivity", "afterTextChanged: " + charSequence.toString());
                        adapter.getFilter().filter(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {

                    // hide soft keyboard
                    getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    GooglePlaceModel place = adapter.getItem(position);
                    autoCompleteTextView.setText(place.getName());
                    Log.d("SearchPlacesActivity", "onCreate: " + place.getName());
                    AddDestinationFragment addDestinationFragment = AddDestinationFragment.newInstance(place, selectedTrip);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container1, addDestinationFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                });

                // Tạo và đặt Adapter cho RecyclerView
                PopularDestinationsAdapter popularDestinationsAdapter = new PopularDestinationsAdapter(popularPlaces, SearchDestinationActivity.this, selectedTrip);
                rvPopularPlaces = findViewById(R.id.rvPopularPlaces);
                rvPopularPlaces.setLayoutManager(new LinearLayoutManager(SearchDestinationActivity.this, RecyclerView.HORIZONTAL, false));
                rvPopularPlaces.setAdapter(popularDestinationsAdapter);

            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        rvPopularPlaces = findViewById(R.id.rvPopularPlaces);
        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });



    }

    public void fetchPopularPlaces(OnDataFetchedListener listener) {
        RetrofitAPI retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=popular+places+in+" + selectedTrip.getArrivalPlace() + "&key=" + getResources().getString(R.string.place_api_key);
        Log.d("SearchPlacesActivity", "fetchPopularPlaces: " + url);
        retrofitAPI.getPopularPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                if (response.isSuccessful()) {
                    GoogleResponseModel googleResponseModel = response.body();
                    if (googleResponseModel.getError() == null) {
                        ArrayList<GooglePlaceModel> data = new ArrayList<>(googleResponseModel.getGooglePlaceModelList());
                        listener.onDataFetched(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<GoogleResponseModel> call, Throwable t) {
                Log.d("SearchPlacesActivity", "onFailure: " + t.getMessage());
            }
        });


    }
}