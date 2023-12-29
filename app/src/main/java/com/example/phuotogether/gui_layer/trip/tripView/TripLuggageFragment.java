package com.example.phuotogether.gui_layer.trip.tripView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;

public class TripLuggageFragment extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_luggage_trip);

        setAndGetAllView();
        setEventClickButtonBack();
        setTitleTripView();
    }

    private void setAndGetAllView() {
        btnBack = findViewById(R.id.buttonBackViewTrip);
        tvTitle = findViewById(R.id.tvTitleViewTrip);
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
