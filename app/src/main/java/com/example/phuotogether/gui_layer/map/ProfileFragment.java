package com.example.phuotogether.gui_layer.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.phuotogether.R;

public class ProfileFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components and set up button click listeners

        ImageButton closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Close the dialog when the close button is clicked
            }
        });

        // Set up other UI components and functions based on your requirements
        // (e.g., load profile data, set up function list, etc.)
    }

    @Override
    public void onStart() {
        super.onStart();
        // Apply the custom style to the dialog
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(920,1200);
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_background); // Add a custom background with corner radius
        }
    }
}
