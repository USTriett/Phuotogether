package com.example.phuotogether.gui_layer.trip.tripView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.example.phuotogether.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class TripLuggageFragment extends Fragment {
    private EditText etAddLuggage;
    private RecyclerView rvLuggageList;
    private ItemAdapter itemAdapter;
    private List<Item> listItem = new ArrayList<>();
    private int itemNo = 0;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_luggage_trip, container, false);

        setAndGetAllView(rootView);
        setEventAddLuggage();

        return rootView;
    }

    private void setEventAddLuggage() {
        etAddLuggage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    String luggageItem = etAddLuggage.getText().toString().trim();
                    if (!luggageItem.isEmpty()) {
                        addItemToItemList(luggageItem);
                        setItemList();
                        etAddLuggage.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setItemList() {
        rvLuggageList.setLayoutManager(new LinearLayoutManager(requireContext()));

        itemAdapter = new ItemAdapter(requireContext(), listItem);
        rvLuggageList.setAdapter(itemAdapter);
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
            listItem.add(new Item(0, itemNo, luggageItem));
            itemNo++;
            setItemList(); // Update the RecyclerView
        }
    }

    private void setAndGetAllView(View rootView) {
        etAddLuggage = rootView.findViewById(R.id.etAddLuggage);
        rvLuggageList = rootView.findViewById(R.id.rvListLuggageItem);
    }

}
