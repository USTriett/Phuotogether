package com.example.phuotogether.gui_layer.trip.destinationList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.PlannedDestination;
import com.example.phuotogether.gui_layer.MainActivity;


import java.util.List;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.ViewHolder>{
    private List<PlannedDestination> destinationList;
    private Context mContext;

    public DestinationListAdapter(Context mContext, List<PlannedDestination> destinationList) {
        this.mContext = mContext;
        this.destinationList = destinationList;
        Log.d("DestinationListAdapter", "DestinationListAdapter: " + destinationList.size());
    }

    @NonNull
    @Override
    public DestinationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_item, parent, false);
        return new DestinationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationListAdapter.ViewHolder holder, int position) {
        PlannedDestination destination = destinationList.get(position);
        holder.bind(destination);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationInfoFragment destinationInfoFragment = DestinationInfoFragment.newInstance(destination);
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.tripschedule, destinationInfoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDestinationName;
        private TextView tvDestinationAddress;
        private TextView tvDestinationTime;
        private TextView tvDestinationNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDestinationName = itemView.findViewById(R.id.tvName);
            tvDestinationAddress = itemView.findViewById(R.id.tvAddress);
            tvDestinationTime = itemView.findViewById(R.id.tvTime);
            tvDestinationNote = itemView.findViewById(R.id.tvNote);

        }

        public void bind(PlannedDestination destination) {
            tvDestinationName.setText(destination.getLocationName());
            tvDestinationAddress.setText(destination.getLocationAddress());
            tvDestinationTime.setText(destination.getBeginTime() + " - " + destination.getEndTime());
            tvDestinationNote.setText(destination.getNote());
        }
    }

}
