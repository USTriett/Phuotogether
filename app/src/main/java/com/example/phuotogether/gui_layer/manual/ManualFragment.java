package com.example.phuotogether.gui_layer.manual;

import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.context;
import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.manual.ManualManager;
import com.example.phuotogether.data_layer.manual.ManualObject;
import com.example.phuotogether.dto.Manual;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManualFragment extends Fragment implements ManualManager.ManualFetchListener{
    public static final int TAB_POSITION = 2;
    View mRootView;

    public static Fragment newInstance() {
        return new ManualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_manual, container, false);
        ManualManager.setManualFetchListener(this);
        return mRootView;
    }

    @Override
    public void onManualListFetched(ArrayList<Manual> manualList) {
        if (getActivity() != null) {
            loadRecyclerView(mRootView, manualList);
        }
    }

    void loadRecyclerView(View rootView, ArrayList<Manual> manualList) {
        RecyclerView manualRecyclerView = rootView.findViewById(R.id.manualRecyclerView);
        manualRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        ManualListAdapter adapter = new ManualListAdapter(requireContext(), manualList);
        manualRecyclerView.setAdapter(adapter);
    }
}