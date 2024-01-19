package com.example.phuotogether.gui_layer.info;

import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFullOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFullOptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;
    private View viewHold;
    private ImageView avatar;

    private EditInfoPopupFragment editFragment;

    private AvatarOptionsFragment avatarOptionsFragment;
    private View logoutBtn;
    private boolean isNightMode;

    public InfoFullOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFullOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFullOptionsFragment newInstance(String param1, String param2) {
        InfoFullOptionsFragment fragment = new InfoFullOptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewHold = inflater.inflate(R.layout.fragment_info_full_options, container, false);
        Button editBtn = (Button) viewHold.findViewById(R.id.editInfoBtn);
        editFragment = new EditInfoPopupFragment();
        user = (User) getArguments().getSerializable("user");
        updateUserInfo(user);

        logoutBtn = (CardView)viewHold.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity activity = (MainActivity) getActivity();
                        User.getInstance().updateInfo(0, false, "", "", "");
                        User.getInstance().updateInfo(null);
                        activity.recreate();

                    }
                }
        );
        isNightMode = getArguments().getBoolean("isDarkMode");
        //Switch nightSwitch = viewHold.findViewById(R.id.nightModeSwitch);
        avatar = viewHold.findViewById(R.id.avatarUser);
        avatar.setAlpha(1.0f);
        Uri selectedImageUri = null;
        avatarOptionsFragment = new AvatarOptionsFragment(avatar, selectedImageUri);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "Avatar Clicked");
                avatarOptionsFragment.show(getFragmentManager(), "Avatar Options");

            }
        });

//        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
////                    buttonView.
//                    Log.d("", "onCheckedChanged: setNightmode");
//                    isNightMode = true;
//                }
//                else{
//                    isNightMode = false;
//                }
//                if(getActivity() instanceof MainActivity){
//                    ((MainActivity) getActivity()).setThemeMode(isNightMode);
//
//                }
//            }
//        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("user", user);
                editFragment.setArguments(args);
                editFragment.show(getFragmentManager(), "edit Dialog");

            }
        });

        return viewHold;
    }
    public void updateUserInfo(User user){
        this.user = user;
        TextView tview  = (TextView) viewHold.findViewById(R.id.usernameTextView);
        tview.setText(user.getFullName());
        Log.d("user", "updateUserInfo: " +user.getFullName());

    }

}