package com.example.phuotogether.gui_layer.trip.destinationList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_access_layer.map.PhotoModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;

import java.util.ArrayList;
import java.util.List;

public class PopularDestinationsAdapter extends RecyclerView.Adapter<PopularDestinationsAdapter.ViewHolder>{
    private ArrayList<GooglePlaceModel> popularPlacesArrayList;
    private Context context;
    private Trip selectedTrip;

    public PopularDestinationsAdapter(ArrayList<GooglePlaceModel> popularPlacesArrayList, Context context, Trip selectedTrip) {
        this.popularPlacesArrayList = popularPlacesArrayList;
        this.context = context;
        this.selectedTrip = selectedTrip;
    }

    @NonNull
    @Override
    public PopularDestinationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_places, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularDestinationsAdapter.ViewHolder holder, int position) {
        GooglePlaceModel popularPlaces = popularPlacesArrayList.get(position);
        holder.tvPopularPlaces.setText(popularPlaces.getName());
        List<PhotoModel> photos = popularPlaces.getPhotos();
        if (photos != null && photos.size() > 0) {
            PhotoModel photo = photos.get(0);
            String photoReference = photo.getPhotoReference();
            String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=" + context.getResources().getString(R.string.place_api_key);
            Glide.with(context).load(imageUrl).into(holder.ivPopularPlaces);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchPlacesActivity", "onCreate: " + popularPlaces.getName());
                AddDestinationFragment addDestinationFragment = AddDestinationFragment.newInstance(popularPlaces, selectedTrip);
                FragmentTransaction fragmentTransaction = ((SearchDestinationActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container1, addDestinationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularPlacesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPopularPlaces;
        ImageView ivPopularPlaces;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPopularPlaces = itemView.findViewById(R.id.tvPopularPlaces);
            ivPopularPlaces = itemView.findViewById(R.id.imgPopularPlaces);
        }
    }

    private class Filter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<GooglePlaceModel> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(popularPlacesArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GooglePlaceModel place : popularPlacesArrayList) {
                    if (place.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(place);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            popularPlacesArrayList.clear();
            popularPlacesArrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((GooglePlaceModel) resultValue).getName();
        }
    }
}
