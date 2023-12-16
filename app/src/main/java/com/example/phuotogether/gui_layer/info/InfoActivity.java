package com.example.phuotogether.gui_layer.info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.phuotogether.R;

import java.io.Serializable;


public class InfoActivity extends AppCompatActivity {

    private InfoFullOptionsFragment fullOptionsFragment;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        user = new UserInfo(); // get User when login success
        fullOptionsFragment = (InfoFullOptionsFragment) getSupportFragmentManager().findFragmentById(R.id.fullOptionsFragment);

//        if (fullOptionsFragment == null) {
            // Instantiate the fragment if it's not already added
            fullOptionsFragment = new InfoFullOptionsFragment();
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            fullOptionsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fullOptionsFragmentContainer, fullOptionsFragment)
                    .commit();
//        }
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
}