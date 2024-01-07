package com.example.phuotogether.gui_layer.trip.destinationList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.map.GooglePlaceModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceAutoCompleteAdapter extends ArrayAdapter<GooglePlaceModel> implements Filterable {

    private List<GooglePlaceModel> places;
    private List<GooglePlaceModel> suggestions ;

    public PlaceAutoCompleteAdapter(Context context, int resource, List<GooglePlaceModel> places) {
        super(context, resource, places);
        this.places = places;
        this.suggestions = new ArrayList<>();
        Log.d("SearchPlacesActivity", "PlaceAutoCompleteAdapter: " + places.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(places.get(position).getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    suggestions.clear();

                    for (GooglePlaceModel place : places) {
                        if (place.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(place);
                        }
                    }

                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("SearchPlacesActivity", "places: " + places.size());
                Log.d("SearchPlacesActivity", "publishResults: " + results.count);
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}

