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
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.dto.Item;
import com.example.phuotogether.dto.Trip;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> luggageList;
    private Context context;
    private TripLuggageManager tripLuggageManager;
    private int tripId;
    private OnLuggageItemActionListener onLuggageItemActionListener;

    public void setOnLuggageItemActionListener(OnLuggageItemActionListener onLuggageItemActionListener) {
        this.onLuggageItemActionListener = onLuggageItemActionListener;
    }


    public ItemAdapter(Context context, List<Item> luggageList, TripLuggageManager tripLuggageManager, int tripId) {
        this.context = context;
        this.luggageList = luggageList;
        this.tripLuggageManager = tripLuggageManager;
        this.tripId = tripId;
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
            Log.d("ItemAdapter", "deleteLuggageItem: " + luggageList.size());
            luggageList.remove(position);

            Log.d("ItemAdapter", "deleteLuggageItem: " + luggageList.size());
            tripLuggageManager.updateItemList(tripId,luggageList,new TripLuggageManager.AddItemCallback() {
                @Override
                public void onAddItemResult(boolean success) {
                    if (success) {
                        notifyItemRemoved(position);
                        if (onLuggageItemActionListener != null) {
                            onLuggageItemActionListener.onItemDeleted(luggageList);
                        }
                        Toast.makeText(context, "Xóa vật dụng thành công", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Xóa vật dụng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void addItemList(List<Item> listItem) {
        Log.d("ItemAdapter", "addItemList: " + listItem.size());
        luggageList.clear();
        luggageList.addAll(listItem);
        notifyDataSetChanged();
    }
    public void addItem(Item item) {
        luggageList.add(item);
        notifyItemInserted(luggageList.size() - 1);
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