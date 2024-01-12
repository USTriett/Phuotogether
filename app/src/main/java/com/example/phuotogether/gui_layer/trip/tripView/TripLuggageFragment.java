package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripView.Luggage;

import java.util.ArrayList;
import java.util.List;

public class TripLuggageFragment extends AppCompatActivity{

    private ImageButton btnBack;
    private TextView tvTitle;
    private RecyclerView rvLuggageList;
    private EditText etAddLuggage;

    private LuggageAdapter luggageAdapter;

    private Trip selectedTrip;

    private TripListManager tripListManager;
    private TripLuggageManager tripLuggageManager;

    private int count_luggage = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_luggage_trip);

        tripListManager = TripListManager.getInstance();
        tripLuggageManager = TripLuggageManager.getInstance();

        setAndGetAllView();
        setSelectedTrip();
        setEventClickButtonBack();
        setTitleTripView();
        setEventEnterETAddLuggage();
        setLuggageList();
    }


    private void setEventEnterETAddLuggage() {
        etAddLuggage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    String luggageItem = etAddLuggage.getText().toString().trim();
                    if (!luggageItem.isEmpty()) {
                        int tripID = selectedTrip.getTripID();
                        tripLuggageManager.addLuggage(luggageItem,false,tripID,count_luggage);
                        count_luggage++;
                        etAddLuggage.setText("");
                        setLuggageList();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setSelectedTrip() {
        Intent intent = getIntent();
        if (intent != null) {
            int tripPosition = intent.getIntExtra("trip_position", -1);

            if (tripPosition != -1) {
                selectedTrip = tripListManager.getTripAtPosition(tripPosition);
            }
        }
    }

    private void setLuggageList() {
        rvLuggageList.setLayoutManager(new LinearLayoutManager(this));

        int tripID = selectedTrip.getTripID();

        List<Luggage> tripLuggage = tripLuggageManager.getLuggageListByID(tripID);

        luggageAdapter = new LuggageAdapter(this, tripLuggage);
        rvLuggageList.setAdapter(luggageAdapter);
    }

    private void setAndGetAllView() {
        btnBack = findViewById(R.id.buttonBackViewTrip);
        tvTitle = findViewById(R.id.tvTitleViewTrip);
        rvLuggageList = findViewById(R.id.rvListLuggageItem);
        etAddLuggage = findViewById(R.id.etAddLuggage);
    }

    private void setEventClickButtonBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTitleTripView() {
        // Thiết lập tiêu đề cho fragment tại đây nếu cần
    }
}
