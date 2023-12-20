//package com.example.phuotogether.gui_layer.auth.SignUp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.example.phuotogether.R;
//import com.example.phuotogether.businesslogic_layer.auth.SignUp.SignUpManager;
//
//import java.util.regex.Pattern;
//
//public class SignUpActivity extends AppCompatActivity {
//    TextView tvPrivacyPolicy, tvLoginNav;
//    EditText etEmail, etPassword, etConfirmPassword;
//    AppCompatButton btnSignUp;
//    CheckBox checkBox;
//    ImageButton btnBack;
//    TextView tvEmptyEmail, tvEmptyPassword, tvEmptyConfirmPassword, tvNoneCheck;
//    TextView tvInvalidEmail, tvInvalidPassword, tvWrongConfirmPassword;
//    private SignUpManager signUpManager;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        signUpManager = new SignUpManager();
//
//        setAndGetAllView();
//        setEventClickSignUpButton();
//        setEventClickBackButton();
//        setEventClickLoginTextView();
//    }
//
//    private void setEventClickLoginTextView() {
//        tvLoginNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
//            }
//        });
//    }
//
//    private void setEventClickBackButton() {
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//    }
//
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    private void setEventClickSignUpButton() {
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String stringEmail = etEmail.getText().toString();
//                String stringPassword = etPassword.getText().toString();
//                String stringConfirmPassword = etConfirmPassword.getText().toString();
//                Boolean isCheckBoxChecked = getCheckBoxState();
//
//                if (signUpManager.isSuccessSignUp(stringEmail,stringPassword,stringConfirmPassword,isCheckBoxChecked)){
//                    showSuccessToast();
//                } else {
//                    signUpManager.handleSignUpError(stringEmail, stringPassword, stringConfirmPassword, isCheckBoxChecked,tvEmptyEmail,tvEmptyPassword,tvEmptyConfirmPassword,tvInvalidEmail,tvInvalidPassword,tvWrongConfirmPassword,tvNoneCheck);
//                }
//            }
//        });
//    }
//
//    private void showSuccessToast() {
//        Context context = getApplicationContext();
//        CharSequence text = "Đăng ký thành công!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//    }
//
//
//    private Boolean getCheckBoxState() {
//        return checkBox.isChecked();
//    }
//
//    private void setAndGetAllView() {
//        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicySignup);
//        tvLoginNav = findViewById(R.id.tvLoginNavSignup);
//        etEmail = findViewById(R.id.editTextEmailSignup);
//        etPassword = findViewById(R.id.editTextPasswordSignup);
//        etConfirmPassword = findViewById(R.id.editTextConfirmPasswordSignup);
//        btnSignUp = findViewById(R.id.buttonSignUpSignup);
//        checkBox = findViewById(R.id.myCheckBoxSignup);
//        btnBack = findViewById(R.id.buttonBackSignup);
//        tvEmptyEmail = findViewById(R.id.tvEmptyEmailNotificationSignup);
//        tvEmptyPassword = findViewById(R.id.tvEmptyPasswordNotificationSignup);
//        tvEmptyConfirmPassword = findViewById(R.id.tvEmptyConfirmPasswordNotificationSignup);
//        tvInvalidEmail = findViewById(R.id.tvInvalidEmailNotificationSignup);
//        tvInvalidPassword = findViewById(R.id.tvInvalidPasswordNotificationSignup);
//        tvWrongConfirmPassword = findViewById(R.id.tvWrongConfirmPasswordNotificationSignup);
//        tvNoneCheck = findViewById(R.id.tvNoneCheckNotificationSignup);
//    }
//}
//
