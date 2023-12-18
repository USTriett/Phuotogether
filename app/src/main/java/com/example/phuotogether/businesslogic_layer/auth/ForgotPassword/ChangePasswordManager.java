package com.example.phuotogether.businesslogic_layer.auth.ForgotPassword;

import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ChangePasswordManager {
    public boolean isSuccessChangePassword(String newPassword, String confirmPassword){
        return (newPassword.equals(confirmPassword) && isValidPassword(newPassword));
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        if (!containsUppercase(password)) {
            return false;
        }

        if (!containsSpecialCharacter(password)) {
            return false;
        }

        return true;
    }

    private boolean containsSpecialCharacter(String password) {
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
        return specialCharPattern.matcher(password).find();
    }

    private boolean containsUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public void handleChangePasswordError(String newPassword, String confirmPassword, TextView tvEmptyNewPassword, TextView tvInvalidNewPassword, TextView tvEmptyConfirmPassword, TextView tvWrongConfirmPassword){
        if (newPassword.isEmpty()){
            tvEmptyNewPassword.setVisibility(View.VISIBLE);
        } else {
            tvEmptyNewPassword.setVisibility(View.GONE);
            if (!isValidPassword(newPassword)){
                tvInvalidNewPassword.setVisibility(View.VISIBLE);
            } else {
                tvInvalidNewPassword.setVisibility(View.GONE);
            }
        }
        if (confirmPassword.isEmpty()){
            tvEmptyConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            tvEmptyConfirmPassword.setVisibility(View.GONE);
            if (!confirmPassword.equals(newPassword)){
                tvWrongConfirmPassword.setVisibility(View.VISIBLE);
            }
        }
    }
}
