package com.example.phuotogether.gui_layer.trip.destinationList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phuotogether.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDestinationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDestinationFragment extends Fragment {

    private String mParam1;
    private String mParam2;

    public SearchDestinationFragment() {
        // Required empty public constructor
    }

    public static SearchDestinationFragment newInstance(String param1, String param2) {
        SearchDestinationFragment fragment = new SearchDestinationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_destination, container, false);
    }
}