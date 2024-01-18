package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.databinding.FragmentAddTripBinding;
import com.example.phuotogether.databinding.FragmentSettingTripBinding;
import com.example.phuotogether.dto.User;

public class TripSettingFragment extends Fragment {
    private static Trip selectedTrip;
    private FragmentSettingTripBinding binding;
    private static TripListManager selectedTripListManager;

    public static TripSettingFragment newInstance(Trip trip, TripListManager tripListManager) {
        selectedTrip = trip;
        selectedTripListManager = tripListManager;
        return new TripSettingFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentSettingTripBinding.inflate(inflater, container, false);

        setEventCLickSaveButton();

        return binding.getRoot();
    }

    private void setEventCLickSaveButton() {
        binding.buttonSaveChangeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Thay đổi hành trình");
                builder.setMessage("Bạn có muốn thay đổi thông tin hành trình?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    String tripName = binding.etChangeNameSetting.getText().toString();
                    String startDes = binding.etChangeStartDesSetting.getText().toString();
                    String endDes = binding.etChangeEndDateSetting.getText().toString();
                    String startDate = binding.etChangeStartDateSetting.getText().toString();
                    String endDate = binding.etChangeEndDateSetting.getText().toString();

                    selectedTripListManager.updateTrip(selectedTrip, tripName, startDate, endDate, startDes, endDes, new TripListManager.UpdateTripCallback() {
                        @Override
                        public void onUpdateTripResult(boolean success) {
                            if (success) {
                                showSuccessToast();
                                requireActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(requireContext(), "Chỉnh sửa hành trình thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            }
        });
    }

    private void showSuccessToast() {
        Context context = requireContext();
        CharSequence text = "Chỉnh sửa hành trình thành công";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}