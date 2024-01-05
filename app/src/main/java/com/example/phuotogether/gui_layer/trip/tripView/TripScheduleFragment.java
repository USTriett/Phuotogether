package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phuotogether.R;
import com.example.phuotogether.databinding.FragmentTripScheduleBinding;

import java.util.ArrayList;
import java.util.List;

public class TripScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentTripScheduleBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripScheduleFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static TripScheduleFragment newInstance() {
        TripScheduleFragment fragment = new TripScheduleFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentTripScheduleBinding.inflate(inflater, container, false);


        binding.edtSearchPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Intent intent = new Intent(getActivity(), SearchPlacesActivity.class);
                startActivity(intent);
            }
        });

//                setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchPlacesFragment searchPlacesFragment = new SearchPlacesFragment();
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .add(R.id.tripschedule, searchPlacesFragment)
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        Log.e("TripScheduleFragment", "onResume: ");
        super.onResume();
        // Set the visibility of edtSearchPlace to View.VISIBLE when coming back from SearchPlacesFragment
        binding.edtSearchPlace.setVisibility(View.VISIBLE);
    }

}