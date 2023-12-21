package com.example.phuotogether.gui_layer.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;

public class InfoFragment extends Fragment {
    public static final int TAB_POSITION = 3;

    private InfoFullOptionsFragment fullOptionsFragment;
    private UserInfo user;
    private boolean isDarkMode = false; // handle du lieu
    private boolean savedInstanceStateUsed = false; // Flag to check if savedInstanceState is used
    public static Fragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        if (savedInstanceState != null) {
            // Retrieve the isDarkMode state from savedInstanceState
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false);
            savedInstanceStateUsed = true;
        }

        if (getActivity() != null) {
            // Initialize the user (replace this with your actual user initialization logic)
            user = new UserInfo();

            // Initialize the InfoFullOptionsFragment
            fullOptionsFragment = new InfoFullOptionsFragment();
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            args.putBoolean("isDarkMode", isDarkMode);

            fullOptionsFragment.setArguments(args);

            // Check if the fragment is added for the first time
            if (!savedInstanceStateUsed) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fullOptionsFragmentContainer, fullOptionsFragment)
                        .commit();
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isDarkMode", isDarkMode);
        outState.putSerializable("user", user);
    }
}