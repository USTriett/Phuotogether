package com.example.phuotogether.businesslogic_layer.auth.SignUp;

import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SignUpManager {
    public boolean isSuccessSignUp(String email, String password, String confirmPassword, Boolean isCheckBoxChecked){
        return (isCheckBoxChecked && isValidEmail(email) && isValidPassword(password) && confirmPassword.equals(password));
    }

    public void handleSignUpError(String stringEmail, String stringPassword, String stringConfirmPassword, Boolean isCheckBoxChecked, TextView tvEmptyEmail, TextView tvEmptyPassword, TextView tvEmptyConfirmPassword, TextView tvInvalidEmail, TextView tvInvalidPassword, TextView tvWrongConfirmPassword, TextView tvNoneCheck){
        if (!stringEmail.isEmpty()){
            tvEmptyEmail.setVisibility(View.GONE);
        }
        if (!stringPassword.isEmpty()){
            tvEmptyPassword.setVisibility(View.GONE);
        }
        if (!stringConfirmPassword.isEmpty()){
            tvEmptyConfirmPassword.setVisibility(View.GONE);
        }
        if (stringEmail.isEmpty()){
            tvEmptyEmail.setVisibility(View.VISIBLE);
        }
        if (stringPassword.isEmpty()){
            tvEmptyPassword.setVisibility(View.VISIBLE);
        }
        if (stringConfirmPassword.isEmpty()){
            tvEmptyConfirmPassword.setVisibility(View.VISIBLE);
        }
        if (!isValidEmail(stringEmail) && !stringEmail.isEmpty()){
            tvInvalidEmail.setVisibility(View.VISIBLE);
        }
        if (isValidEmail(stringEmail)){
            tvInvalidEmail.setVisibility(View.GONE);
        }
        if (!isValidPassword(stringPassword) && !stringPassword.isEmpty()){
            tvInvalidPassword.setVisibility(View.VISIBLE);
        }
        if (isValidPassword(stringPassword)){
            tvInvalidPassword.setVisibility(View.GONE);
        }
        if (!stringConfirmPassword.equals(stringPassword) && !stringConfirmPassword.isEmpty()){
            tvWrongConfirmPassword.setVisibility(View.VISIBLE);
        }
        if (stringConfirmPassword.equals(stringPassword) && !stringConfirmPassword.isEmpty()){
            tvWrongConfirmPassword.setVisibility(View.GONE);
        }
        if (!isCheckBoxChecked){
            tvNoneCheck.setVisibility(View.VISIBLE);
        }
        if (isCheckBoxChecked){
            tvNoneCheck.setVisibility(View.GONE);
        }
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
}
