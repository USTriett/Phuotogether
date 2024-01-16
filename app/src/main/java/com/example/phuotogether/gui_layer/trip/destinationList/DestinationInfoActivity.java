package com.example.phuotogether.gui_layer.trip.destinationList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.PlannedDestination;

public class DestinationInfoActivity extends AppCompatActivity {
    private PlannedDestination selectedDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);

        selectedDestination = (PlannedDestination) getIntent().getSerializableExtra("destination");

        TextView tvHeader = findViewById(R.id.tvHeader);
        TextView tvLocationName = findViewById(R.id.tvName);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvDateTime = findViewById(R.id.tvDateTime);
        ImageButton btnBack = findViewById(R.id.btnBack);

        tvHeader.setText("Địa điểm / " + selectedDestination.getLocationName());
        tvLocationName.setText(selectedDestination.getLocationName());
        tvAddress.setText(selectedDestination.getLocationAddress());
        tvDateTime.setText(selectedDestination.getBeginTime() + " - " + selectedDestination.getEndTime());

        btnBack.setOnClickListener(v -> {
            finish();
        });

    }
}