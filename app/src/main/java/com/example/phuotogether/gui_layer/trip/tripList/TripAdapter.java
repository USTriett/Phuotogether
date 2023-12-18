package com.example.phuotogether.gui_layer.trip.tripList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.trip.tripList.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.bind(trip);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private TextView tripNameTextView;
        private TextView tripDescriptionTextView;
        private ImageView tripImageView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripNameTextView = itemView.findViewById(R.id.tripName);
            tripDescriptionTextView = itemView.findViewById(R.id.tripTime);
            tripImageView = itemView.findViewById(R.id.tripImage);
        }

        public void bind(Trip trip) {
            tripNameTextView.setText(trip.getTripName());
            tripDescriptionTextView.setText(trip.getTripTime());
            tripImageView.setImageResource(trip.getTripImageID());
        }
    }
}
