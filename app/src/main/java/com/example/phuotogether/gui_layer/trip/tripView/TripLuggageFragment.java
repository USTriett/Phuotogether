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

public class TripLuggageFragment extends Fragment {

    private ImageButton btnBack;
    private TextView tvTitle;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_luggage_trip, container, false);

        setAndGetAllView(rootView);
        //setEventClickButtonBack();
        setTitleTripView();

        return rootView;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.fragment_luggage_trip);
//
//        setAndGetAllView();
//        setEventClickButtonBack();
//        setTitleTripView();
//    }

    private void setAndGetAllView(View rootView) {
        btnBack = rootView.findViewById(R.id.buttonBackViewTrip);
        tvTitle = rootView.findViewById(R.id.tvTitleViewTrip);
    }

    private void setEventClickButtonBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setTitleTripView() {
        // Thiết lập tiêu đề cho fragment tại đây nếu cần
    }
}
