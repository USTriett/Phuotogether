package com.example.phuotogether.gui_layer.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.phuotogether.R;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.info.InfoFragment;

public class ProfileFragment extends DialogFragment {
    private TextView offlineMapsTextView;
    private TextView profileTextView;
    private View viewHolder;
    private void binding(){
        if(viewHolder != null)
        {
            offlineMapsTextView = viewHolder.findViewById(R.id.offlineMapsTextView);
            profileTextView = viewHolder.findViewById(R.id.myProfileTextView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewHolder = inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        binding();
        return viewHolder;
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
        profileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(InfoFragment.TAB_POSITION);
            }
        });
    }
    public TextView getDownloadOfflineMapTextView(){
        return offlineMapsTextView;
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
