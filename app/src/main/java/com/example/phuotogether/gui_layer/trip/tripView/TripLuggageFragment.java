package com.example.phuotogether.gui_layer.trip.tripView;

import static androidx.core.content.ContextCompat.getSystemService;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class TripLuggageFragment extends Fragment implements OnLuggageItemActionListener{
    private EditText etAddLuggage;
    private RecyclerView rvLuggageList;
    private ItemAdapter itemAdapter;
    public List<Item> listItem = new ArrayList<>();
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

        itemAdapter = new ItemAdapter(requireContext(), listItem, tripLuggageManager, selectedTrip.getId());
        itemAdapter.setOnLuggageItemActionListener(this);

        rvLuggageList.setAdapter(itemAdapter);
    }
    @Override
    public void onItemDeleted(List<Item> itemList) {
        Log.d("TripLuggageFragment", "onItemDeleted: " + itemList.size());

        // Clear the existing items in listItem and add the new items
        listItem.clear();
        listItem.addAll(itemList);

        // Notify the adapter about the updated list
        itemAdapter.addItemList(listItem);
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
                Toast.makeText(requireContext(), "Đã có vật dụng này trong danh sách", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        // If the item doesn't exist, add it to the list
        if (!itemExists) {
            Item item = new Item(selectedTrip.getId(), itemNo, luggageItem);
            Log.d("TripLuggageFragment", "addItemToItemList: " + listItem.size());
            listItem.add(item);
            Log.d("TripLuggageFragment", "addItemToItemList: " + listItem.size());
            tripLuggageManager.updateItemList(selectedTrip.getId(), listItem, new TripLuggageManager.AddItemCallback() {
                @Override
                public void onAddItemResult(boolean success) {
                    if (success) {
                        itemAdapter.addItem(item);
                        itemNo++;
                    }
                }
            });
            // Update the RecyclerView
        }
    }



}