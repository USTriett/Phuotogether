package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.manual.ManualItemFragment;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddtripFragment;

public class TripViewFragment extends Fragment {
    public static final int TAB_POSITION = 1;
    private CardView cvTripLuggage, cvTripSchedule, cvTripDiary, cvTripSetting;
    private ImageButton btnBack;
    private TextView tvTitle;
    private TripListManager tripListManager;
    private Trip selectedTrip;


    public static TripViewFragment newInstance() {return new TripViewFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_view_trip, container, false);

        tripListManager = TripListManager.getInstance();

        setAndGetAllView(rootView);
        setSelectedTrip();
        setTitleTripView();
        setEventClickButtonBack();
        setEventClickCardViewLuggage();
        return rootView;
    }

    private void setEventClickCardViewLuggage() {
        cvTripLuggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                int tripPosition = -1;

                if (bundle != null) {
                    tripPosition = bundle.getInt("trip_position", -1);
                }

                Intent intent = new Intent(requireActivity(), TripLuggageFragment.class);
                if (tripPosition != -1) {
                    intent.putExtra("trip_position", tripPosition);
                }
                startActivity(intent);
            }
        });
    }

    private void setSelectedTrip() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            int tripPosition = bundle.getInt("trip_position", -1);

            if (tripPosition != -1) {
                selectedTrip = tripListManager.getTripAtPosition(tripPosition);
            }
        }
    }

    private void setTitleTripView() {
        String titleTripView = selectedTrip.getTripName();
        tvTitle.setText("Hành trình / " + titleTripView);
    }

    private void setEventClickButtonBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    private void setAndGetAllView(View rootView) {
        btnBack = rootView.findViewById(R.id.buttonBackViewTrip);
        tvTitle = rootView.findViewById(R.id.tvTitleViewTrip);

        cvTripLuggage = rootView.findViewById(R.id.tripLuggage);
        cvTripSchedule = rootView.findViewById(R.id.tripSchedule);
        cvTripDiary = rootView.findViewById(R.id.tripDiary);
        cvTripSetting = rootView.findViewById(R.id.tripSetting);
    }
}
