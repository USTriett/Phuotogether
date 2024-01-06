package com.example.phuotogether.gui_layer.trip.addTrip;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;

public class AddtripFragment extends Fragment {

    int mPosition;

    private EditText etNameTrip, etStartDes, etGoalDes, etStartDate, etEndDate;
    private ImageButton btnBack;
    private Button btnSave;

    private TripListManager tripListManager;
    public AddtripFragment() {
    }

    public static AddtripFragment newInstance() {
        return new AddtripFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_add_trip, container, false);

        tripListManager = TripListManager.getInstance();

        setAndGetAllView(rootView);
        setEventClickBackButton();
        setEventClickSaveButton();

        return rootView;
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
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    private void showSuccessToast() {
        Context context = requireContext();
        CharSequence text = "Thêm hành trình thành công";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setEventClickBackButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setAndGetAllView(View view) {
        etNameTrip = view.findViewById(R.id.etNameTripAddTrip);
        etStartDes = view.findViewById(R.id.etStartDesAddTrip);
        etGoalDes = view.findViewById(R.id.etGoalDesAndTrip);
        etStartDate = view.findViewById(R.id.etStartDateAddTrip);
        etEndDate = view.findViewById(R.id.etEndDateAddTrip);
        btnBack = view.findViewById(R.id.buttonBackAddTrip);
        btnSave = view.findViewById(R.id.buttonSaveAddTrip);
    }
}