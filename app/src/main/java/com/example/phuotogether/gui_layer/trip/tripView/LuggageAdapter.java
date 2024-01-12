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
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.data_layer.trip.tripView.Luggage;
import com.example.phuotogether.data_layer.trip.tripView.TripLuggageDatabase;

import java.util.List;

public class LuggageAdapter extends RecyclerView.Adapter<LuggageAdapter.ViewHolder> {

    private List<Luggage> luggageList;
    private Context context;
    private OnLuggageItemActionListener listener;


    public LuggageAdapter(Context context, List<Luggage> luggageList) {
        this.context = context;
        this.luggageList = luggageList;
    }

    public void setOnLuggageItemActionListener(OnLuggageItemActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.luggage_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Luggage luggageItem = luggageList.get(position);

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
            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return luggageList.size();
    }

    public void changeLuggageName(int position, String newName) {
        Luggage currentLuggage = luggageList.get(position);
        currentLuggage.setName(newName);
        notifyItemChanged(position);
        Toast.makeText(context, "Name changed to " + newName, Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        CheckBox checkbox;
        TextView textView;
        ImageButton btnDelete;
        GestureDetector gestureDetector;
        LuggageAdapter adapter;

        public ViewHolder(@NonNull View itemView, LuggageAdapter adapter) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkboxLuggage);
            textView = itemView.findViewById(R.id.textViewLuggage);
            btnDelete = itemView.findViewById(R.id.btnDeleteLuggageItem);


            this.adapter = adapter;

            gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    showInputDialog();
                    Log.d("doubleTap", "oke");
                    return true;
                }
            });

            itemView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return false;
        }

        private void showInputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Enter new name");

            final EditText input = new EditText(itemView.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newName = input.getText().toString();
                    if (!TextUtils.isEmpty(newName)) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            adapter.changeLuggageName(position, newName);
                        }
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }
}


