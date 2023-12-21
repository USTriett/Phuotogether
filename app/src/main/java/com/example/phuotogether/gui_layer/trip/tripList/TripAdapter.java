package com.example.phuotogether.gui_layer.trip.tripList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.manual.ManualItemFragment;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;
import com.example.phuotogether.gui_layer.trip.addTrip.AddTripFragment;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> mTripList;
    public static final int TAB_POSITION = 1;
    private Context mContext;

    public TripAdapter(Context context, List<Trip> tripList) {
        mContext = context;
        mTripList = tripList;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                addFragment(ManualItemFragment.newInstance(itemPosition), TAB_POSITION);
                AddTripFragment addTripFragment = new AddTripFragment(itemPosition);

                FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.triplist, addTripFragment);
                transaction.addToBackStack(null); // Add to back stack if needed
                transaction.commit();
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
