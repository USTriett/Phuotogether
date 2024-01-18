package com.example.phuotogether.gui_layer.trip.addTrip;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.databinding.FragmentAddTripBinding;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.FragmentUpdateListener;
import com.example.phuotogether.gui_layer.MainActivity;

public class AddtripFragment extends Fragment {
    private User user;
    private FragmentAddTripBinding binding;
    private TripListManager tripListManager;
    public AddtripFragment(User user) {
        this.user = user;
        Log.d("AddtripFragment", "AddtripFragment: " + user.getId());
        tripListManager = TripListManager.getInstance(user);


    }
    public static AddtripFragment newInstance(User user) {
        return new AddtripFragment(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentAddTripBinding.inflate(inflater, container, false);
        setEventClickSaveButton();
        setEventClickBackButton();
        return binding.getRoot();
    }

    private void setEventClickSaveButton() {
        binding.buttonSaveAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = binding.etNameTripAddTrip.getText().toString();
                String startDes = binding.etStartDesAddTrip.getText().toString();
                String goalDes = binding.etGoalDesAndTrip.getText().toString();
                String startDate = binding.etStartDateAddTrip.getText().toString();
                String endDate = binding.etEndDateAddTrip.getText().toString();

                Log.d("AddtripFragment", "onClick: " + tripName + " " + startDes + " " + goalDes + " " + startDate + " " + endDate);
                tripListManager.addTrip(user, tripName, startDate, endDate, startDes, goalDes, new TripListManager.AddTripCallback() {
                            @Override
                            public void onAddTripResult(boolean success) {
                                if (success) {
                                    showSuccessToast();
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(requireContext(), "Thêm hành trình thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
        binding.buttonBackAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}