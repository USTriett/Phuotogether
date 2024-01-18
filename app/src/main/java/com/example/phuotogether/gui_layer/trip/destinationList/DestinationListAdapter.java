package com.example.phuotogether.gui_layer.trip.destinationList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.TripDestinationsManager;
import com.example.phuotogether.dto.PlannedDestination;
import com.example.phuotogether.gui_layer.MainActivity;


import java.util.List;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.ViewHolder>{
    private List<PlannedDestination> destinationList;
    private Context mContext;
    private TripDestinationsManager tripDestinationsManager;
    private OnUpdateDestinationListenr onUpdateDestinationListenr;

    public void setOnUpdateDestinationListenr(OnUpdateDestinationListenr onUpdateDestinationListenr) {
        this.onUpdateDestinationListenr = onUpdateDestinationListenr;
    }

    public DestinationListAdapter(Context mContext, List<PlannedDestination> destinationList, TripDestinationsManager tripDestinationsManager) {
        this.mContext = mContext;
        this.destinationList = destinationList;
        this.tripDestinationsManager = tripDestinationsManager;
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
        private ImageButton btnDeleteDestination;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDestinationName = itemView.findViewById(R.id.tvName);
            tvDestinationAddress = itemView.findViewById(R.id.tvAddress);
            tvDestinationTime = itemView.findViewById(R.id.tvTime);
            tvDestinationNote = itemView.findViewById(R.id.tvNote);
            btnDeleteDestination = itemView.findViewById(R.id.btnDeleteDes);
        }

        public void bind(PlannedDestination destination) {
            tripDestinationsManager.getLocation(destination.getLocationID(), new TripDestinationsManager.FetchLocationCallback() {
                @Override
                public void onFetchLocationResult(boolean success, com.example.phuotogether.dto.Location location) {
                    if (success) {

                        tvDestinationName.setText(location.getName());
                        tvDestinationAddress.setText(location.getAddress());
                        // example of time: 2021-05-20T00:00:00
                        // get begin time from T
                        String beginTime = destination.getBeginTime().split("T")[1];
                        String endTime = destination.getEndTime().split("T")[1];
                        tvDestinationTime.setText(beginTime + " - " + endTime);
                        tvDestinationNote.setText(destination.getNote());
                    }
                }
            });

            btnDeleteDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(destination);

//                    tripDestinationsManager.deleteDestination(destination, new TripDestinationsManager.DeleteDestinationCallback() {
//                        @Override
//                        public void onDeleteDestinationResult(boolean success) {
//                            if (success) {
//                                destinationList.remove(destination);
//                                notifyDataSetChanged();
//                                if (onUpdateDestinationListenr != null) {
//                                    onUpdateDestinationListenr.onUpdateDestination();
//                                }
//                            }
//                        }
//                    });
                }
            });
        }

        private void showDialog(PlannedDestination destination) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Xóa điểm đến");
            builder.setMessage("Bạn có chắc chắn muốn xóa điểm đến này không?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                tripDestinationsManager.deleteDestination(destination, new TripDestinationsManager.DeleteDestinationCallback() {
                    @Override
                    public void onDeleteDestinationResult(boolean success) {
                        if (success) {
                            destinationList.remove(destination);
                            notifyDataSetChanged();
                            if (onUpdateDestinationListenr != null) {
                                onUpdateDestinationListenr.onUpdateDestination();
                            }
                        }
                    }
                });
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        }
    }

}
