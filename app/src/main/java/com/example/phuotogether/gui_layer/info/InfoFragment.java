package com.example.phuotogether.gui_layer.info;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.User;


public class InfoFragment extends Fragment {
    public static final int TAB_POSITION = 3;
    private InfoFullOptionsFragment fullOptionsFragment;
    private User user;
    private boolean isDarkMode = false; // handle data
    private boolean savedInstanceStateUsed = false; // Flag to check if savedInstanceState is used

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        if (savedInstanceState != null) {
            // Retrieve the isDarkMode state from savedInstanceState
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false);
            savedInstanceStateUsed = true;
        }

        if (!savedInstanceStateUsed) {
            user = new User(); // get User when login success
            fullOptionsFragment = new InfoFullOptionsFragment();
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            args.putBoolean("isDarkMode", isDarkMode);
            Log.d("TAG", "onCreateView: running");

            fullOptionsFragment.setArguments(args);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fullOptionsFragmentContainer, fullOptionsFragment)
                    .commit();
        } // get User when login success
        else {
            user = (User) savedInstanceState.getSerializable("user");
            fullOptionsFragment = (InfoFullOptionsFragment) getChildFragmentManager().getFragment(savedInstanceState, "InfoFragment");
        }

        return view;
    }

    public void updateUser(User user) {
        fullOptionsFragment.updateUserInfo(user);
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the isDarkMode state
        outState.putBoolean("isDarkMode", isDarkMode);
        outState.putSerializable("user", user);
        getChildFragmentManager().putFragment(outState, "InfoFragment", fullOptionsFragment);
    }
}

//public class InfoFragment extends Fragment {
//
//    private InfoFullOptionsFragment fullOptionsFragment;
//    private User user;
//    private boolean isDarkMode = false; // handle du lieu
//    private boolean savedInstanceStateUsed = false; // Flag to check if savedInstanceState is used
//    public static Fragment newInstance() {
//        return new InfoFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
//
//        if (savedInstanceState != null) {
//            // Retrieve the isDarkMode state from savedInstanceState
//            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false);
//            savedInstanceStateUsed = true;
//        }
//
//        if (getActivity() != null) {
//            // Initialize the user (replace this with your actual user initialization logic)
//            user = new User();
//
//            // Initialize the InfoFullOptionsFragment
//            fullOptionsFragment = new InfoFullOptionsFragment();
//            Bundle args = new Bundle();
//            args.putSerializable("user", user);
//            args.putBoolean("isDarkMode", isDarkMode);
//
//            fullOptionsFragment.setArguments(args);
//
//            // Check if the fragment is added for the first time
//            if (!savedInstanceStateUsed) {
//                getChildFragmentManager().beginTransaction()
//                        .replace(R.id.fullOptionsFragmentContainer, fullOptionsFragment)
//                        .commit();
//            }
//        }
//
//        return rootView;
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean("isDarkMode", isDarkMode);
//        outState.putSerializable("user", user);
//    }
//}