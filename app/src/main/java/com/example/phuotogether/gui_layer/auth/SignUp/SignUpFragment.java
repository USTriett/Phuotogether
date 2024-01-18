package com.example.phuotogether.gui_layer.auth.SignUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.auth.SignUp.SignUpManager;
import com.example.phuotogether.data_layer.auth.UserDatabase;
import com.example.phuotogether.databinding.FragmentSignUpBinding;
import com.example.phuotogether.gui_layer.auth.SignIn.SignInFragment;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private SignUpManager signUpManager;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentSignUpBinding.inflate(inflater, container, false);
        signUpManager = new SignUpManager(new UserDatabase());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvLoginNavSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FrameLayout frameLayout = getActivity().findViewById(R.id.signup_container);
                requireActivity().getSupportFragmentManager().beginTransaction().remove(SignUpFragment.this).commit();
                frameLayout.setVisibility(View.GONE);
            }
        });

        setEventClickSignUpButton();
    }

    private void setEventClickSignUpButton() {
        binding.buttonSignUpSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringEmail = binding.editTextEmailSignup.getText().toString();
                String stringPassword = binding.editTextPasswordSignup.getText().toString();
                String stringConfirmPassword = binding.editTextConfirmPasswordSignup.getText().toString();
                String stringFullName = binding.editTextFullNameSignup.getText().toString();
                Boolean isCheckBoxChecked = getCheckBoxState();

                boolean isValid = signUpManager.handleSignUpError(stringEmail, stringPassword, stringConfirmPassword, isCheckBoxChecked, binding.tvEmptyEmailNotificationSignup
                        , binding.tvEmptyPasswordNotificationSignup
                        , binding.tvEmptyConfirmPasswordNotificationSignup
                        , binding.tvInvalidEmailNotificationSignup
                        , binding.tvInvalidPasswordNotificationSignup
                        , binding.tvWrongConfirmPasswordNotificationSignup
                        , binding.tvNoneCheckNotificationSignup);

                if (!isValid) {
                    return;
                }
                Log.d("TAG", "onClick: " + stringEmail + stringPassword + stringConfirmPassword + stringFullName + isCheckBoxChecked);
                signUpManager.isSuccessSignUp(stringEmail, stringPassword, stringFullName, new SignUpManager.SignUpCallback() {
                    @Override
                    public void onSignUpResult(boolean success) {
                        if (success) {
                            Log.d("TAG", "onSignUpResult: " + success);
                            // show loading
                            loadingHandler();
                        }
                    }
                });
            }
        });
    }

    private void loadingHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FrameLayout frameLayout = getActivity().findViewById(R.id.signup_container);
                requireActivity().getSupportFragmentManager().beginTransaction().remove(SignUpFragment.this).commit();
                frameLayout.setVisibility(View.GONE);
            }
        }, 0); // delay
    }

    private Boolean getCheckBoxState() {
        return binding.myCheckBoxSignup.isChecked();
    }
}