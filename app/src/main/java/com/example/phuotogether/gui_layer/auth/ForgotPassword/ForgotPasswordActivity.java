package com.example.phuotogether.gui_layer.auth.ForgotPassword;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.auth.ForgotPassword.ForgotPasswordManager;
import com.example.phuotogether.data_layer.auth.UserDatabase;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText etEmail;
    private AppCompatButton btnSend;
    private TextView tvEmptyEmail, tvWrongEmail;
    private ForgotPasswordManager forgotPasswordManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordManager = new ForgotPasswordManager(new UserDatabase());

        setAndGetAllView();
        setEventClickSendButton();
        setEventClickBackButton();
    }

    private void setEventClickBackButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setEventClickSendButton() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringEmail = etEmail.getText().toString();
                if (!forgotPasswordManager.isSuccessForgotPassword(stringEmail)){
                    forgotPasswordManager.handleForgotPasswordError(stringEmail,tvEmptyEmail,tvWrongEmail);
                } else {
                    tvEmptyEmail.setVisibility(View.GONE);
                    tvWrongEmail.setVisibility(View.GONE);
                    showCustomAlertDialog("KIỂM TRA EMAIL CỦA BẠN");
                }
            }
        });
    }

    private void showCustomAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_checkemail_forgot_password, null);

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        AppCompatButton checkEmailButton = dialogView.findViewById(R.id.btnCheckEmailForgotPassword);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        checkEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class));
                alertDialog.hide();
            }
        });
    }

    private void setAndGetAllView() {
        btnBack = findViewById(R.id.buttonBack);
        etEmail = findViewById(R.id.editTextEmailForgotPassword);
        btnSend = findViewById(R.id.buttonSend);
        tvEmptyEmail = findViewById(R.id.tvEmptyEmailNotificationForgotPassword);
        tvWrongEmail = findViewById(R.id.tvWrongEmailNotificationForgotPassword);
    }
}
