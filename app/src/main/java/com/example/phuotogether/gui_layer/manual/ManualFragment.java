package com.example.phuotogether.gui_layer.manual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.manual.ManualObject;

import java.util.ArrayList;

public class ManualFragment extends Fragment {
    public static final int TAB_POSITION = 2;
    ArrayList<ManualObject> mManualObjects;

    public static Fragment newInstance() {
        return new ManualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manual, container, false);
        if (getActivity() != null) {
            fetchManualObjects();
            loadRecyclerView(rootView);
        }
        return rootView;
    }

    void fetchManualObjects(){
        mManualObjects = new ArrayList<>();
        mManualObjects.add(new ManualObject("Ngộ độc thực phẩm"));
        mManualObjects.add(new ManualObject("Rắn cắn"));
        mManualObjects.add(new ManualObject("Chấn thương"));
        mManualObjects.add(new ManualObject("Lạc trong rừng"));
        mManualObjects.add(new ManualObject("Tìm đồ ăn, nguồn nước"));
        mManualObjects.add(new ManualObject("Ngộ độc thực phẩm"));
        mManualObjects.add(new ManualObject("Rắn cắn"));
        mManualObjects.add(new ManualObject("Chấn thương"));
        mManualObjects.add(new ManualObject("Lạc trong rừng"));
        mManualObjects.add(new ManualObject("Tìm đồ ăn, nguồn nước"));
        mManualObjects.add(new ManualObject("Ngộ độc thực phẩm"));
        mManualObjects.add(new ManualObject("Rắn cắn"));
        mManualObjects.add(new ManualObject("Chấn thương"));
        mManualObjects.add(new ManualObject("Lạc trong rừng"));
        mManualObjects.add(new ManualObject("Tìm đồ ăn, nguồn nước"));
    }

    void loadRecyclerView(View rootView) {
        RecyclerView manualRecyclerView = rootView.findViewById(R.id.manualRecyclerView);
        manualRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        ManualListAdapter adapter = new ManualListAdapter(requireContext(), mManualObjects);
        manualRecyclerView.setAdapter(adapter);
    }
}