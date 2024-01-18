package com.example.phuotogether.gui_layer.info;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.phuotogether.R;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditInfoPopupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EditInfoPopupFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User user;
    private EditText usernameEditText;
    private EditText phoneNumberEditText;
    private boolean isNightMode;
    private View viewHold;
    public EditInfoPopupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditInfoPopupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditInfoPopupFragment newInstance(String param1, String param2) {
        EditInfoPopupFragment fragment = new EditInfoPopupFragment();
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

        viewHold = inflater.inflate(R.layout.fragment_edit_info_popup, container, false);
        Button closeEditFormBtn = viewHold.findViewById(R.id.closeEditFormBtn);
        Bundle args = getArguments();
        user = args != null ? (User) args.getSerializable("user") : User.getInstance();
        isNightMode = args.getBoolean("isDarkMode");
        usernameEditText = viewHold.findViewById(R.id.usernameEditText);
        usernameEditText.setText(user.getFullName());
        phoneNumberEditText = viewHold.findViewById(R.id.phoneNumberEditText);
        phoneNumberEditText.setText(user.getPhoneNumber());




        closeEditFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click or dismiss the dialog

                if(getActivity() instanceof MainActivity){
                    MainActivity activity = (MainActivity) getActivity();
                    activity.updateUserInfo(user);
                }
                dismiss();
            }
        });
        return viewHold;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        // Handle dismiss event
    }


}