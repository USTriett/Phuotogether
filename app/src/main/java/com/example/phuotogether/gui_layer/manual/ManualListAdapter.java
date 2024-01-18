package com.example.phuotogether.gui_layer.manual;

import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.context;
import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.fetchManualList;
import static com.example.phuotogether.businesslogic_layer.manual.ManualManager.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.manual.ManualObject;
import com.example.phuotogether.dto.Manual;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.gui_layer.navigation.MainFragmentPagerAdapter;

import java.io.File;
import java.util.ArrayList;

public class ManualListAdapter extends RecyclerView.Adapter<ManualViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Manual> mManualList;
    private Context mContext;
    public static final int TAB_POSITION = 2;
    private final int COUNT_DEFAULT_MANUALS = 2;

    public ManualListAdapter(Context context, ArrayList<Manual> manualList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mManualList = manualList;
    }

    @NonNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.manual_item, parent, false);
        return new ManualViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {

        // Display data on UI
        Manual manual = mManualList.get(position);
        holder.mManualItemTitleView.setText(manual.getTitle());

        // Set OnClick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                if (itemPosition < COUNT_DEFAULT_MANUALS){
                    addFragment(ManualItemFragment.newInstance(itemPosition, manual.getContent()), TAB_POSITION);
                    ManualItemFragment manualItemFragment = new ManualItemFragment(itemPosition, manual.getContent());

                    FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext())
                            .getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.manual, manualItemFragment);
                    transaction.addToBackStack(null); // Add to back stack if needed
                    transaction.commit();
                }
                else {
                    addFragment(ManualItemFragmentRead.newInstance(itemPosition, manual.getContent()), TAB_POSITION);
                    ManualItemFragmentRead manualItemFragmentRead = new ManualItemFragmentRead(itemPosition, manual.getContent());

                    FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext())
                            .getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.manual, manualItemFragmentRead);
                    transaction.addToBackStack(null); // Add to back stack if needed
                    transaction.commit();
                }

            }
        });

        // Set OnLongClick
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            int itemPosition = holder.getAdapterPosition();

            public boolean onLongClick(View v) {
                if (itemPosition >= COUNT_DEFAULT_MANUALS) {
                    showRemoveConfirmationDialog(itemPosition); // Show confirmation dialog
                    return true; // Consume the long click event
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mManualList.size();
    }

    public void addFragment(Fragment fragment, int tabPosition) {
        MainFragmentPagerAdapter pagerAdapter = ((MainActivity) mContext).getPagerAdapter();
        pagerAdapter.updateFragment(fragment, tabPosition);
    }

    private void showRemoveConfirmationDialog(int itemPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remove Item")
                .setMessage("Bạn có muốn xóa cẩm nang này không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFileInStorage(itemPosition);
                        fetchManualList();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void removeFileInStorage(int itemPosition){
        // lay thu muc
        String userId = String.valueOf(user.getId());
        File userFolder = new File(context.getFilesDir(), userId);

        // xoa file
        String fileToBeDeletedName = "manual" + itemPosition + ".xml";
        File fileToBeDeleted = new File(userFolder, fileToBeDeletedName);
        if (fileToBeDeleted.exists()) {
            if (fileToBeDeleted.delete()) {
                Log.d("", "File deleted successfully");
            } else {
                // Error occurred while deleting the file
            }
        } else {
            // File does not exist
        }

        // doi ten cac file con lai
        for (int i = itemPosition; i < getItemCount(); i++) {
            String oldFileName = "manual" + (i + 1) + ".xml";
            File oldFile = new File(userFolder, oldFileName);
            String newFileName = "manual" + i + ".xml";
            File newFile = new File(userFolder, newFileName);
            if (oldFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    Log.d("file renamed", String.valueOf(i+1));
                } else {
                    // Error occurred while renaming the file
                }
            } else {
                // File does not exist
            }
        }
    }
}