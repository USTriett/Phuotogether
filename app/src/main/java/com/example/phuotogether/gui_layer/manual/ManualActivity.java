package com.example.phuotogether.gui_layer.manual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.manual.ManualObject;

import java.util.ArrayList;

public class ManualActivity extends AppCompatActivity {

    ArrayList<ManualObject> mManualObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_manual);

        fetchManualObjects();
        loadRecyclerView();
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

    void loadRecyclerView(){
        RecyclerView manualRecyclerView = findViewById(R.id.manualRecyclerView);
        manualRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ManualListAdapter adapter = new ManualListAdapter(this, mManualObjects);
        manualRecyclerView.setAdapter(adapter);
    }
}