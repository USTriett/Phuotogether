package com.example.phuotogether.gui_layer.trip.tripView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.Item;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> luggageList;
    private Context context;


    public ItemAdapter(Context context, List<Item> luggageList) {
        this.context = context;
        this.luggageList = luggageList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.luggage_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item luggageItem = luggageList.get(position);

        holder.checkbox.setChecked(luggageItem.isChecked());
        holder.textView.setText(luggageItem.getName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                deleteLuggageItem(adapterPosition);
            }
        });
    }

    private void deleteLuggageItem(int position) {
        if (position != RecyclerView.NO_POSITION) {
            luggageList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return luggageList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkbox;
        TextView textView;
        ImageButton btnDelete;
        ItemAdapter adapter;

        public ViewHolder(@NonNull View itemView, ItemAdapter adapter) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkboxLuggage);
            textView = itemView.findViewById(R.id.textViewLuggage);
            btnDelete = itemView.findViewById(R.id.btnDeleteLuggageItem);

            this.adapter = adapter;
        }

    }
}