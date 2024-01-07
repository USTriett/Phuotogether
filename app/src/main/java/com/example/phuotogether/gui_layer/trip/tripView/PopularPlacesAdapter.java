package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;

import java.util.ArrayList;

public class PopularPlacesAdapter extends RecyclerView.Adapter<PopularPlacesAdapter.ViewHolder>{
    private ArrayList<String> popularPlacesArrayList;
    private Context context;

    public PopularPlacesAdapter(ArrayList<String> popularPlacesArrayList, Context context) {
        this.popularPlacesArrayList = popularPlacesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PopularPlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_places, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPlacesAdapter.ViewHolder holder, int position) {
        String popularPlaces = popularPlacesArrayList.get(position);
        holder.tvPopularPlaces.setText(popularPlaces);
        holder.tvPopularPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 4/27/2023
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularPlacesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPopularPlaces;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPopularPlaces = itemView.findViewById(R.id.tvPopularPlaces);
        }
    }
}
