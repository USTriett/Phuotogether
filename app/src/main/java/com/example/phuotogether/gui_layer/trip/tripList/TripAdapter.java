package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripList.TripListManager;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.map.PhotoModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.manual.ManualItemFragment;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddtripFragment;
import com.example.phuotogether.gui_layer.trip.tripView.TripViewFragment;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> mTripList;
//    public static final int TAB_POSITION = 1;
    private Context mContext;
    private TripListManager tripListManager;

    public TripAdapter( Context context, List<Trip> tripList, TripListManager tripListManager) {
        mContext = context;
        mTripList = tripList;
        this.tripListManager = tripListManager;
        Log.d("TripAdapter", "TripAdapter: " + mTripList.size());
    }

    public void setTripListManager(TripListManager tripListManager) {
        this.tripListManager = tripListManager;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = mTripList.get(position);
        holder.bind(trip);

        tripListManager.getPlace(trip.getArrivalPlace(), new TripListManager.OnPlaceFetchedListener() {
            @Override
            public void onDataFetched(GooglePlaceModel googlePlaceModel) {
                List<PhotoModel> photos = googlePlaceModel.getPhotos();
                if (photos != null && photos.size() > 0) {
                    PhotoModel photo = photos.get(0);
                    String photoReference = photo.getPhotoReference();
                    String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=" + mContext.getResources().getString(R.string.place_api_key);
                    Glide.with(mContext).load(imageUrl).into(holder.tripImageView);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();

                Bundle bundle = new Bundle();
                bundle.putInt("trip_position", itemPosition);
                bundle.putSerializable("trip", trip);

//                addFragment(ManualItemFragment.newInstance(itemPosition), TAB_POSITION);
                TripViewFragment tripViewFragment = TripViewFragment.newInstance(trip, tripListManager);
                tripViewFragment.setArguments(bundle);

                FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.triplist, tripViewFragment);
                transaction.addToBackStack(null); // Add to back stack if needed
                transaction.commit();
            }
        });
        holder.tripDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDeleteDialog(trip, holder);
            }
        });
    }

    private void displayDeleteDialog(Trip trip, TripViewHolder holder) {
        FragmentManager fm = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Delete Trip");
        builder.setMessage("Are you sure you want to delete this trip?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            int itemPosition = holder.getAdapterPosition();
            mTripList.remove(itemPosition);
            notifyItemRemoved(itemPosition);
            notifyItemRangeChanged(itemPosition, mTripList.size());
            deleteTripFromDatabase(trip);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();

    }

    private void deleteTripFromDatabase(Trip trip) {
        tripListManager.deleteTrip(trip, new TripListManager.DeleteTripCallback() {
            @Override
            public void onDeleteTripResult(boolean success) {
                if (success) {
                    Log.d("TripListDatabase", "onDeleteTripResult: " + success);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    public void addFragment(Fragment fragment, int tabPosition) {
        MainFragmentPagerAdapter pagerAdapter = ((MainActivity) mContext).getPagerAdapter();
        pagerAdapter.updateFragment(fragment, tabPosition);
    }

    public void setTripList(List<Trip> finalTripList) {
        mTripList = finalTripList;
        notifyDataSetChanged();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private TextView tripNameTextView;
        private TextView tripDescriptionTextView;
        private ImageView tripImageView;
        private ImageButton tripDeleteButton;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripNameTextView = itemView.findViewById(R.id.tripName);
            tripDescriptionTextView = itemView.findViewById(R.id.tripTime);
            tripImageView = itemView.findViewById(R.id.tripImage);
            tripDeleteButton = itemView.findViewById(R.id.btnDeleteTrip);
        }

        public void bind(Trip trip) {
            tripNameTextView.setText(trip.getTripName());
            tripDescriptionTextView.setText(trip.getStartDate() + " ~ " + trip.getEndDate());
        }
    }
}
