package com.example.phuotogether.gui_layer.trip.tripView;

import static androidx.core.content.ContextCompat.getSystemService;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripView.TripLuggageManager;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class TripLuggageFragment extends Fragment {
    private EditText etAddLuggage;
    private RecyclerView rvLuggageList;
    private ItemAdapter itemAdapter;
    private List<Item> listItem = new ArrayList<>();
    private static Trip selectedTrip;
    private TripLuggageManager tripLuggageManager;
    private int itemNo = 0;

    public static TripLuggageFragment newInstance(Trip trip) {
        selectedTrip = trip;
        return new TripLuggageFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_luggage_trip, container, false);
        tripLuggageManager = new TripLuggageManager(selectedTrip.getId());
        fetchItemListFromDatabase();
        setAndGetAllView(rootView);
        setEventAddLuggage();

        return rootView;
    }
    private void setAndGetAllView(View rootView) {
        etAddLuggage = rootView.findViewById(R.id.etAddLuggage);
        rvLuggageList = rootView.findViewById(R.id.rvListLuggageItem);

        rvLuggageList.setLayoutManager(new LinearLayoutManager(requireContext()));

        itemAdapter = new ItemAdapter(requireContext(), listItem);

        rvLuggageList.setAdapter(itemAdapter);
    }

    private void fetchItemListFromDatabase() {
        tripLuggageManager.getItemList(new TripLuggageManager.FetchItemCallback() {
            @Override
            public void onFetchItemResult(boolean success, List<Item> luggageList) {
                if (success) {
                    listItem = luggageList;
                    itemNo = listItem.size() + 1;
                    itemAdapter.addItemList(listItem);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setEventAddLuggage() {
        etAddLuggage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    String luggageItem = etAddLuggage.getText().toString().trim();
                    if (!luggageItem.isEmpty()) {
                        addItemToItemList(luggageItem);
                        etAddLuggage.setText("");
                    }
                    // hide soft keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(requireContext(), InputMethodManager.class);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }


    private void addItemToItemList(String luggageItem) {
        // Check if the item is already in the list
        boolean itemExists = false;
        for (Item item : listItem) {
            if (item.getName().equals(luggageItem)) {
                itemExists = true;
                break;
            }
        }

        // If the item doesn't exist, add it to the list
        if (!itemExists) {
            listItem.add(new Item(selectedTrip.getId(), itemNo, luggageItem));
            tripLuggageManager.updateItemList(selectedTrip.getId(), listItem, new TripLuggageManager.AddItemCallback() {
                @Override
                public void onAddItemResult(boolean success) {
                    if (success) {
                        itemAdapter.addItemList(listItem);
                        itemAdapter.notifyDataSetChanged();
                        itemNo++;
                    }
                }
            });
             // Update the RecyclerView
        }
    }



}
