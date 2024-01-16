package com.example.phuotogether.businesslogic_layer.auth.SignIn;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.phuotogether.data_layer.auth.UserDatabase;
import com.example.phuotogether.data_layer.auth.UserResponse;
import com.example.phuotogether.dto.User;

public class SignInManager  {
    private UserDatabase userRepository;
    private boolean isSuccess = false;
    public SignInManager(UserDatabase userRepository) {
        this.userRepository = userRepository;
    }

    public interface SignInCallback {
        void onSignInResult(boolean success, User user);
    }
    public void isSuccessSignIn(String emailString, String passwordString, SignInCallback callback) {
        userRepository.isSuccessSignIn(emailString, passwordString, new UserDatabase.SignInCallback() {
            @Override
            public void onSignInResult(boolean success, User user) {
                if (success) {
                    // call back to SignInFragment
                    callback.onSignInResult(true, user);
                } else {
                    callback.onSignInResult(false, user);
                }
            }
        });
    }



    public void handleSignInError(TextView tvEmptyEmail, TextView tvEmptyPassword, TextView tvWrongEmail, TextView tvWrongPassword) {
        tvEmptyEmail.setVisibility(View.GONE);
        tvEmptyPassword.setVisibility(View.GONE);
        tvWrongEmail.setVisibility(View.VISIBLE);
        tvWrongPassword.setVisibility(View.VISIBLE);
    }

    public void validate(String emailString, String passwordString, TextView tvEmptyEmail, TextView tvEmptyPassword, TextView tvWrongEmail, TextView tvWrongPassword) {
        if (emailString.isEmpty() && !passwordString.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.VISIBLE);
        } else if (passwordString.isEmpty() && !emailString.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.VISIBLE);
        } else if (emailString.isEmpty() && passwordString.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.VISIBLE);
            tvEmptyPassword.setVisibility(View.VISIBLE);
        } else{
            tvEmptyEmail.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.GONE);
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
        }
    }
}
