package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;

public class AddTripActivity extends AppCompatActivity {
    private EditText etNameTrip, etStartDes, etGoalDes, etStartDate, etEndDate;
    private ImageButton btnBack;
    private Button btnSave;

    private TripListManager tripListManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtrip);

        tripListManager = TripListManager.getInstance();

        setAndGetAllView();
        setEventClickBackButton();
        setEventClickSaveButton();
    }

    private void setEventClickSaveButton() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = etNameTrip.getText().toString();
                String startDes = etStartDes.getText().toString();
                String goalDes = etGoalDes.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();

                if (tripListManager.isSuccessAddTrip(tripName, startDes, goalDes, startDate, endDate)){
                    tripListManager.addTrip(tripName,startDate,endDate);
                    showSuccessToast();
                    startActivity(new Intent(AddTripActivity.this, TripListActivity.class));
                }
            }
        });
    }

    private void showSuccessToast() {
        Context context = getApplicationContext();
        CharSequence text = "Thêm hành trình thành công";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setEventClickBackButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setAndGetAllView() {
        etNameTrip = findViewById(R.id.etNameTripAddTrip);
        etStartDes = findViewById(R.id.etStartDesAddTrip);
        etGoalDes = findViewById(R.id.etGoalDesAndTrip);
        etStartDate = findViewById(R.id.etStartDateAddTrip);
        etEndDate = findViewById(R.id.etEndDateAddTrip);
        btnBack = findViewById(R.id.buttonBackAddTrip);
        btnSave = findViewById(R.id.buttonSaveAddTrip);
    }
}
