package com.example.phuotogether.gui_layer.manual;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;

public class ManualViewHolder extends RecyclerView.ViewHolder {
    public CardView mManualIteamCardView;
    public TextView mManualItemTitleView;

    public ManualViewHolder(View itemView){
        super(itemView);
        mManualIteamCardView = itemView.findViewById(R.id.manualItemCardView);
        mManualItemTitleView = itemView.findViewById(R.id.manualItemTitleView);
    }
}