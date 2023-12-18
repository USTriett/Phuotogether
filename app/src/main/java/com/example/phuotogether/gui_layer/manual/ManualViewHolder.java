package com.example.phuotogether.gui_layer.manual;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;

public class ManualViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;

    public ManualViewHolder(View itemView){
        super(itemView);
        mTitleTextView = itemView.findViewById(R.id.manualItemTitle);
    }
}