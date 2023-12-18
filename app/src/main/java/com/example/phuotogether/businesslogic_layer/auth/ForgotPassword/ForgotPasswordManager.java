package com.example.phuotogether.businesslogic_layer.auth.ForgotPassword;

import android.view.View;
import android.widget.TextView;

import com.example.phuotogether.data_layer.auth.UserDatabase;

public class ForgotPasswordManager {
    private UserDatabase userDatabase;

    public ForgotPasswordManager(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    public boolean isSuccessForgotPassword(String emailString){
        return userDatabase.isSuccessForgotPassword(emailString);
    }

    public void handleForgotPasswordError(String email, TextView tvEmptyEmail, TextView tvWrongEmail){
        if (email.isEmpty()){
            tvEmptyEmail.setVisibility(View.VISIBLE);
        } else {
            tvEmptyEmail.setVisibility(View.GONE);
        }
        if (!isSuccessForgotPassword(email) && !email.isEmpty()){
            tvWrongEmail.setVisibility(View.VISIBLE);
        }
    }
}
