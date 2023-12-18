package com.example.phuotogether.gui_layer.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;

import com.example.phuotogether.R;

import java.io.Serializable;


public class InfoActivity extends AppCompatActivity {

    private InfoFullOptionsFragment fullOptionsFragment;
    private UserInfo user;
    private boolean isDarkMode = false; // handle du lieu
    private boolean savedInstanceStateUsed = false; // Flag to check if savedInstanceState is used

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Retrieve the isDarkMode state from savedInstanceState
            isDarkMode = savedInstanceState.getBoolean("isDarkMode", false);
            savedInstanceStateUsed = true;

        }
        setContentView(R.layout.activity_info);
        if (!savedInstanceStateUsed) {
            user = new UserInfo(); // get User when login success
            fullOptionsFragment = new InfoFullOptionsFragment();
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            args.putBoolean("isDarkMode", isDarkMode);
            Log.d("TAG", "onCreate: running");

            fullOptionsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fullOptionsFragmentContainer, fullOptionsFragment)
                    .commit();

        } // get User when login success
        else{
            user = (UserInfo) savedInstanceState.getSerializable("user");
            fullOptionsFragment = (InfoFullOptionsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "InfoFragment");
        }

//        if (fullOptionsFragment == null) {
            // Instantiate the fragment if it's not already added

//        DialogFragment dialogFragment = new DialogFragment();

//        Button editInfoBtn = (Button) fullOptionsFragment.getEditInfoBtn();
//        editInfoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogFragment.show(getSupportFragmentManager(), "Edit user information");
//            }
//        });

    }

    public void updateUserInfo(UserInfo user) {
        fullOptionsFragment.updateUserInfo(user);
    }
    public void setThemeMode(boolean isDarkMode){
        this.isDarkMode = isDarkMode;
       if(isDarkMode){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
       }
       else {
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

       }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the isDarkMode state
        outState.putBoolean("isDarkMode", isDarkMode);
        outState.putSerializable("user", user);
        getSupportFragmentManager().putFragment(outState, "InfoFragment", fullOptionsFragment);
//        fullOptionsFragment.onSaveInstanceState(outState);

    }
}