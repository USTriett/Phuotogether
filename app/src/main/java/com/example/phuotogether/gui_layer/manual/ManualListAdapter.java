package com.example.phuotogether.gui_layer.manual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.manual.ManualObject;

import java.util.ArrayList;

public class ManualListAdapter extends RecyclerView.Adapter<ManualViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ManualObject> mManualObjects;

    public ManualListAdapter(Context context, ArrayList<ManualObject> manualObjects) {
        mInflater = LayoutInflater.from(context);
        mManualObjects = manualObjects;
    }

    @NonNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.manual_item, parent, false);
        return new ManualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {
        ManualObject manualObject = mManualObjects.get(position);
        holder.mTitleTextView.setText(manualObject.getTitle());
    }

    @Override
    public int getItemCount() {
        return mManualObjects.size();
    }
}