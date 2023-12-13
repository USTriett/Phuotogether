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

public class ForgotPasswordActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText etEmail;
    private AppCompatButton btnSend;
    private TextView tvEmptyEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

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
                if (stringEmail.isEmpty()){
                    tvEmptyEmail.setVisibility(View.VISIBLE);
                } else {
                    tvEmptyEmail.setVisibility(View.GONE);
                    showCustomAlertDialog("KIỂM TRA EMAIL CỦA BẠN");
                }
            }
        });
    }

    private void showCustomAlertDialog(String message) {
//        Log.d("ButtonClicked", "Send button clicked");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_checkemail_forgot_password, null);

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        AppCompatButton checkEmailButton = dialogView.findViewById(R.id.btnCheckEmailForgotPassword);
        checkEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class));
            }
        });

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void setAndGetAllView() {
        btnBack = findViewById(R.id.buttonBack);
        etEmail = findViewById(R.id.editTextEmailForgotPassword);
        btnSend = findViewById(R.id.buttonSend);
        tvEmptyEmail = findViewById(R.id.tvEmptyEmailNotificationForgotPassword);
    }
}
