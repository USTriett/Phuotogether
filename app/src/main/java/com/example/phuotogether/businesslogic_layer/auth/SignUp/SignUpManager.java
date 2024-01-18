package com.example.phuotogether.businesslogic_layer.auth.SignUp;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.example.phuotogether.data_layer.auth.UserDatabase;

import java.util.regex.Pattern;

public class SignUpManager {
    private UserDatabase userRepository;



    public interface SignUpCallback {
        void onSignUpResult(boolean success, String code);
    }

    public SignUpManager(UserDatabase userRepository) {
        this.userRepository = userRepository;
    }
    public void isSuccessSignUp(String email, String password, String fullName, SignUpCallback callback){
        Log.d("SignUpManager", "isSuccessSignUp: " + email + password + fullName);
        userRepository.isSuccessSignUp(email, password, fullName, new UserDatabase.SignUpCallback() {
            @Override
            public void onSignUpResult(boolean success, String code) {
                if (success) {
                    // call back to SignUpFragment
                    callback.onSignUpResult(true, code);
                } else {
                    callback.onSignUpResult(false, code);
                }
            }
        });
    }

    public boolean handleSignUpError(String stringEmail, String stringPassword,
                                  String stringConfirmPassword,
                                  Boolean isCheckBoxChecked,
                                  TextView tvEmptyEmail,
                                  TextView tvEmptyPassword, TextView tvEmptyConfirmPassword,
                                  TextView tvInvalidEmail, TextView tvInvalidPassword,
                                  TextView tvWrongConfirmPassword, TextView tvNoneCheck){
        boolean isValid = true;
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
            isValid = false;
        }
        if (stringPassword.isEmpty()){
            tvEmptyPassword.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (stringConfirmPassword.isEmpty()){
            tvEmptyConfirmPassword.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (!isValidEmail(stringEmail) && !stringEmail.isEmpty()){
            tvInvalidEmail.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (isValidEmail(stringEmail)){
            tvInvalidEmail.setVisibility(View.GONE);
        }
        if (!isValidPassword(stringPassword) && !stringPassword.isEmpty()){
            tvInvalidPassword.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (isValidPassword(stringPassword)){
            tvInvalidPassword.setVisibility(View.GONE);
        }
        if (!stringConfirmPassword.equals(stringPassword) && !stringConfirmPassword.isEmpty()){
            tvWrongConfirmPassword.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (stringConfirmPassword.equals(stringPassword) && !stringConfirmPassword.isEmpty()){
            tvWrongConfirmPassword.setVisibility(View.GONE);
        }
        if (!isCheckBoxChecked){
            tvNoneCheck.setVisibility(View.VISIBLE);
            isValid = false;
        }
        if (isCheckBoxChecked){
            tvNoneCheck.setVisibility(View.GONE);
        }
        return isValid;
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

    public void validate(String stringEmail, String stringPassword, String stringConfirmPassword, String stringFullName, Boolean isCheckBoxChecked, TextView tvEmptyEmailNotificationSignup, TextView tvEmptyPasswordNotificationSignup, TextView tvEmptyConfirmPasswordNotificationSignup, TextView tvEmptyFullNameNotificationSignup, TextView tvNoneCheckNotificationSignup, TextView tvInvalidEmailNotificationSignup, TextView tvInvalidPasswordNotificationSignup, TextView tvWrongConfirmPasswordNotificationSignup) {

    }
}
