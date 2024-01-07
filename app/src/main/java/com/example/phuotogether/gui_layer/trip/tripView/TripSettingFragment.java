package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;

public class TripSettingFragment extends AppCompatActivity {

    private EditText etNewName;
    private AppCompatButton btnSave, btnDelete;
    private Trip selectedTrip;

    private TripListManager tripListManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting_trip);

        tripListManager = TripListManager.getInstance();

        setAndGetAllView();
        setSelectedTrip();
        setEventClickButtonSave();
        setEventClickButtonDelete();
    }

    private void setEventClickButtonDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setEventClickButtonSave() {
        String newName = etNewName.getText().toString();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newName != null) {
                    selectedTrip.setTripName(newName);
                    Toast.makeText(TripSettingFragment.this, "Đổi tên hành trình", Toast.LENGTH_SHORT).show();
                }
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

    private void setAndGetAllView() {
        etNewName = findViewById(R.id.etNewNameSettingTrip);
        btnSave = findViewById(R.id.buttonSaveChangeSettingTrip);
        btnDelete = findViewById(R.id.buttonDeleteTrip);
    }
}
