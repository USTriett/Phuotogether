package com.example.phuotogether.gui_layer.auth.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.auth.SignIn.SignInManager;
import com.example.phuotogether.data_layer.auth.SignIn.UserDatabase;
import com.example.phuotogether.gui_layer.auth.ForgotPassword.ForgotPasswordActivity;
import com.example.phuotogether.gui_layer.auth.SignUp.SignUpActivity;

public class SignInActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private AppCompatButton btnSignIn, btnSignup;
    private ImageButton btnSignInWithGG, btnSignInWithFB, btnBack;
    private TextView tvForgotPassword, tvEmptyEmail, tvEmptyPassword, tvWrongEmail, tvWrongPassword;
    private SignInManager signInManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signInManager = new SignInManager(new UserDatabase());

        setAndGetAllView();
        setEventClickSignInButton();
        setEventClickSignUpButton();
        setEventClickForgotPasswordTextview();
    }

    private void setEventClickForgotPasswordTextview() {
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void setEventClickSignUpButton() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void setEventClickSignInButton() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = etEmail.getText().toString();
                String passwordString = etPassword.getText().toString();

                if (signInManager.isSuccessSignIn(emailString, passwordString)) {
                    showSuccessToast();
                } else {
                    signInManager.handleSignInError(emailString, passwordString, tvEmptyEmail, tvEmptyPassword, tvWrongEmail, tvWrongPassword);
                }
            }
        });
    }

    private void showSuccessToast() {
        Context context = getApplicationContext();
        CharSequence text = "Đăng nhập thành công!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setAndGetAllView() {
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        tvForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
        btnSignup = findViewById(R.id.buttonSignUp);
        btnSignInWithFB = findViewById(R.id.buttonSignInFacebook);
        btnSignInWithGG = findViewById(R.id.buttonSignInGoogle);
        btnBack = findViewById(R.id.buttonBack);
        tvEmptyEmail = findViewById(R.id.tvEmptyEmailNotification);
        tvWrongEmail = findViewById(R.id.tvWrongEmailNotification);
        tvEmptyPassword = findViewById(R.id.tvEmptyPasswordNotification);
        tvWrongPassword = findViewById(R.id.tvWrongPasswordNotification);
    }
}