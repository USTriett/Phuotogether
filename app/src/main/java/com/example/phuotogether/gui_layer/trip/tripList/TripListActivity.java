package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripListActivity extends AppCompatActivity {

    private ImageButton btnAddTrip;
    private TripAdapter tripAdapter;
    private RecyclerView tripRecyclerView;
    private TripListManager tripListManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplist);

        tripListManager = new TripListManager();

        setAndGetAllView();
        setTripList();
        setEventClickButtonAddTrip();
    }

    private void setEventClickButtonAddTrip() {
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripListActivity.this, AddTripActivity.class));
            }
        });
    }

    private void setAndGetAllView() {
        btnAddTrip = findViewById(R.id.btnAddTrip);
        tripRecyclerView = findViewById(R.id.recycleViewListTrip);
    }

    private void setTripList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tripRecyclerView.setLayoutManager(layoutManager);

        List<Trip> tripList = tripListManager.getTripList();
        tripAdapter = new TripAdapter(tripList);
        tripRecyclerView.setAdapter(tripAdapter);

    }
}
