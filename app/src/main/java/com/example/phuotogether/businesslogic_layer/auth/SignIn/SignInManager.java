package com.example.phuotogether.businesslogic_layer.auth.SignIn;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuotogether.data_layer.auth.SignIn.UserDatabase;
import com.example.phuotogether.gui_layer.auth.SignIn.SignInActivity;

public class SignInManager {
    private UserDatabase userRepository;

    public SignInManager(UserDatabase userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isSuccessSignIn(String emailString, String passwordString) {
        return userRepository.isSuccessSignIn(emailString, passwordString);
    }

    public void handleSignInError(String email, String password, TextView tvEmptyEmail, TextView tvEmptyPassword, TextView tvWrongEmail, TextView tvWrongPassword) {
        // Implement logic to handle error messages
        if (email.isEmpty() && !password.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.VISIBLE);
        } else if (password.isEmpty() && !email.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.VISIBLE);
        } else if (email.isEmpty() && password.isEmpty()){
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
            tvEmptyEmail.setVisibility(View.VISIBLE);
            tvEmptyPassword.setVisibility(View.VISIBLE);
        } else if (!isSuccessSignIn(email, password)){
            tvEmptyEmail.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.GONE);
            tvWrongEmail.setVisibility(View.VISIBLE);
            tvWrongPassword.setVisibility(View.VISIBLE);
        } else{
            tvEmptyEmail.setVisibility(View.GONE);
            tvEmptyPassword.setVisibility(View.GONE);
            tvWrongEmail.setVisibility(View.GONE);
            tvWrongPassword.setVisibility(View.GONE);
        }
    }
}
