package com.example.phuotogether.businesslogic_layer.manual;

import android.content.Context;
import android.util.Log;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.Manual;
import com.example.phuotogether.dto.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ManualManager{
    public static Context context;
    public static User user;
    public static ArrayList<Manual> fetchedManualList = new ArrayList<>();

    public interface ManualFetchListener {
        void onManualListFetched(ArrayList<Manual> manualList);
    }
    private static ManualFetchListener manualFetchListener;
    public static void setManualFetchListener(ManualFetchListener listener) {
        manualFetchListener = listener;
    }

    public static void createInternalManuals() {
        String userId = String.valueOf(user.getId());
        File userFolder = new File(context.getFilesDir(), userId);

        if (!userFolder.exists()) {
            userFolder.mkdirs();
            String[] manualStrings = {
                    context.getString(R.string.manual_food_poisoning),
                    context.getString(R.string.manual_snake_bite)
            };
            for (int i = 0; i < manualStrings.length; i++) {
                String fileName = "manual" + i + ".xml";
                File manualFile = new File(userFolder, fileName);
                try {
                    FileWriter writer = new FileWriter(manualFile);
                    writer.write(manualStrings[i]);
                    writer.close();
                } catch (IOException e) {
                    Log.e("cannot write to file", "");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fetchManualList(){
        // clear previous data
        fetchedManualList.clear();

        // set up links to files
        String userId = String.valueOf(user.getId());
        File userFolder = new File(context.getFilesDir(), userId);
        File manualFiles[] = userFolder.listFiles();

        // read
        for (File manualFile : manualFiles) {
            try {
                FileReader reader = new FileReader(manualFile);
                char[] buffer = new char[(int) manualFile.length()];
                reader.read(buffer);
                String manualContent = new String(buffer);
                fetchedManualList.add(new Manual(manualContent));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Notify the listener when the manual list is fetched
        if (manualFetchListener != null) {
            manualFetchListener.onManualListFetched(fetchedManualList);
        }
    }
}
