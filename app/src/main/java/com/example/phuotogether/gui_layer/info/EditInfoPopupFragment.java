package com.example.phuotogether.gui_layer.info;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuotogether.R;
import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.data_layer.user.UserUpdateRequest;
import com.example.phuotogether.dto.User;
import com.example.phuotogether.gui_layer.MainActivity;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private EditText emailEditText;
    private boolean isNightMode;
    private View viewHold;
    private TextView textView;
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

        usernameEditText = viewHold.findViewById(R.id.usernameEditText);
        usernameEditText.setText(User.getInstance().getFullName());
        emailEditText = (EditText) viewHold.findViewById(R.id.emailEditText);
        emailEditText.setText(User.getInstance().getEmail());
        emailEditText.setEnabled(false);
        textView = viewHold.findViewById(R.id.PasswordChangeText);
        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PasswordChangeFragment fragment = new PasswordChangeFragment();
                        dismiss();
                        fragment.show(getActivity().getSupportFragmentManager(), "");

                    }
                }
        );


        closeEditFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click or dismiss the dialog

                if(getActivity() instanceof MainActivity){
                    MainActivity activity = (MainActivity) getActivity();
                    activity.updateFragments();
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
    private void performChangeName(String name) {
        UserUpdateRequest request = new UserUpdateRequest(User.getInstance().getId(), User.getInstance().getPassword(), name);
        RetrofitAPI myApi = RetrofitClient.getRetrofitClientUser().create(RetrofitAPI.class);

        // Make the API call
        Call<UserResponse> call = myApi.updateUser(request);
        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();

                if(response.isSuccessful()){
                    User.getInstance().updateInfo(
                            User.getInstance().getId(),
                            false,
                            User.getInstance().getEmail(),
                            User.getInstance().getPassword(),
                            name
                    );
                    ((MainActivity) getActivity()).updateFragments();
                    dismissNow();
                    Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_LONG);
                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_LONG);
                dismissNow();
            }
        });
    }

}