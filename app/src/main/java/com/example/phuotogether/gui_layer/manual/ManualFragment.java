package com.example.phuotogether.gui_layer.manual;

import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.context;
import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.manual.ManualManager;
import com.example.phuotogether.data_layer.manual.ManualObject;
import com.example.phuotogether.dto.Manual;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManualFragment extends Fragment implements ManualManager.ManualFetchListener{
    public static final int TAB_POSITION = 2;
    View mRootView;
    int mItemCount;

    public static Fragment newInstance() {
        return new ManualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ManualManager.setManualFetchListener(this);
        mRootView = inflater.inflate(R.layout.fragment_manual, container, false);
        this.setBtnAddOnClickListener();
        return mRootView;
    }

    @Override
    public void onManualListFetched(ArrayList<Manual> manualList) {
        if (getActivity() != null) {
            loadRecyclerView(mRootView, manualList);
        }
    }

    void loadRecyclerView(View rootView, ArrayList<Manual> manualList) {
        // UI
        RecyclerView manualRecyclerView = rootView.findViewById(R.id.manualRecyclerView);
        manualRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        // Data
        ManualListAdapter adapter = new ManualListAdapter(requireContext(), manualList);
        manualRecyclerView.setAdapter(adapter);
        mItemCount = adapter.getItemCount();
    }

    void setBtnAddOnClickListener(){
        ImageButton btnAddManual = mRootView.findViewById(R.id.btnAddManual);
        btnAddManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManualItemFragmentEdit manualItemFragmentEdit = new ManualItemFragmentEdit(mItemCount, "", "");
                addFragment(manualItemFragmentEdit, TAB_POSITION);

                FragmentTransaction transaction = ((FragmentActivity) mRootView.getContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.manual, manualItemFragmentEdit);
                transaction.addToBackStack(null);                           // Add to back stack if needed
                transaction.commit();
            }
        });
    }

    public void addFragment(Fragment fragment, int tabPosition) {
        MainFragmentPagerAdapter pagerAdapter = ((MainActivity) getContext()).getPagerAdapter();
        pagerAdapter.updateFragment(fragment, tabPosition);
    }
}