package com.example.phuotogether.gui_layer.manual;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_access_layer.manual.ManualObject;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.FragmentUpdateCallback;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;

import java.util.ArrayList;

public class ManualListAdapter extends RecyclerView.Adapter<ManualViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ManualObject> mManualObjects;
    private Context mContext;
    public static final int TAB_POSITION = 2;

    public ManualListAdapter(Context context, ArrayList<ManualObject> manualObjects) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mManualObjects = manualObjects;
    }

    @NonNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.manual_item, parent, false);
        return new ManualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {
        ManualObject manualObject = mManualObjects.get(position);
        holder.mTitleTextView.setText(manualObject.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                addFragment(ManualItemFragment.newInstance(itemPosition), TAB_POSITION);
                ManualItemFragment manualItemFragment = new ManualItemFragment(itemPosition);

                FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext())
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.manual, manualItemFragment);
                transaction.addToBackStack(null); // Add to back stack if needed
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mManualObjects.size();
    }

    public void addFragment(Fragment fragment, int tabPosition) {
        MainFragmentPagerAdapter pagerAdapter = ((MainActivity) mContext).getPagerAdapter();
        pagerAdapter.updateFragment(fragment, tabPosition);
    }
}