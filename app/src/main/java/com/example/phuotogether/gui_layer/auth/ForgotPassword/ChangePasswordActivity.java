//package com.example.phuotogether.gui_layer.auth.ForgotPassword;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.example.phuotogether.R;
//import com.example.phuotogether.businesslogic_layer.auth.ForgotPassword.ChangePasswordManager;
//
//import java.util.regex.Pattern;
//
//public class ChangePasswordActivity extends AppCompatActivity {
//    private EditText etNewPassword, etConfirmPassword;
//    private ImageButton btnBack;
//    private AppCompatButton btnChangePassword;
//    private TextView tvEmptyNewPassword, tvEmptyConfirmPassword, tvWrongConfirmPassword, tvInvalidNewPassword;
//    private ChangePasswordManager changePasswordManager;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_password);
//
//        changePasswordManager = new ChangePasswordManager();
//
//        setAndGetAllView();
//        setEventClickBackButton();
//        setEventClickChangePasswordButton();
//    }
//
//    private void setEventClickChangePasswordButton() {
//        btnChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newPassword = etNewPassword.getText().toString();
//                String confirmPassword = etConfirmPassword.getText().toString();
//
//                if (!changePasswordManager.isSuccessChangePassword(newPassword,confirmPassword)){
//                    changePasswordManager.handleChangePasswordError(newPassword,confirmPassword,tvEmptyNewPassword,tvInvalidNewPassword,tvEmptyConfirmPassword,tvWrongConfirmPassword);
//                } else {
//                    tvWrongConfirmPassword.setVisibility(View.GONE);
//                    showCustomAlertDialog("ĐẶT LẠI MẬT KHẨU THÀNH CÔNG MỜI BẠN QUAY LẠI TRANG ĐĂNG NHẬP");
//                }
//
//            }
//        });
//    }
//
//    private void showCustomAlertDialog(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.popup_success_change_password, null);
//
//        TextView messageTextView = dialogView.findViewById(R.id.tvSuccessChange);
//        messageTextView.setText(message);
//
//        AppCompatButton checkEmailButton = dialogView.findViewById(R.id.btnSignInNav);
//        checkEmailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ChangePasswordActivity.this, SignInActivity.class));
//            }
//        });
//
//        builder.setView(dialogView);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.show();
//    }
//
//    private boolean checkValidPassword(String stringPassword) {
//        if (stringPassword.length() < 8) {
//            return false;
//        }
//
//        if (!containsUppercase(stringPassword)) {
//            return false;
//        }
//
//        if (!containsSpecialCharacter(stringPassword)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean containsSpecialCharacter(String stringPassword) {
//        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
//        return specialCharPattern.matcher(stringPassword).find();
//    }
//
//    private boolean containsUppercase(String stringPassword) {
//        for (char c : stringPassword.toCharArray()) {
//            if (Character.isUpperCase(c)) {
//                return true;
//            }
//        }
//        return false;
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
//    private void setAndGetAllView() {
//        btnBack = findViewById(R.id.buttonBack);
//        etConfirmPassword = findViewById(R.id.editTextConfirmPasswordChangePassword);
//        etNewPassword = findViewById(R.id.editTextNewPasswordChangePassword);
//        btnChangePassword = findViewById(R.id.buttonChangePasswordChangePassword);
//        tvEmptyNewPassword = findViewById(R.id.tvEmptyNewPasswordNotificationForgotPassword);
//        tvEmptyConfirmPassword = findViewById(R.id.tvEmptyConfirmPasswordNotificationForgotPassword);
//        tvWrongConfirmPassword = findViewById(R.id.tvWrongConfirmPasswordNotificationForgotPassword);
//        tvInvalidNewPassword = findViewById(R.id.tvInvalidPasswordNotificationForgotPassword);
//    }
//}
