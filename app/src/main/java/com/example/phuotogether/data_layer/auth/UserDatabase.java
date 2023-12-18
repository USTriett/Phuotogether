package com.example.phuotogether.data_layer.auth;

public class UserDatabase {
    private static final String VALID_EMAIL = "test@gmail.com";
    private static final String VALID_PASSWORD = "Test@2003";

    public boolean isSuccessSignIn(String email, String password) {
        return email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD);
    }

    public boolean isSuccessForgotPassword(String email) {
        return email.equals(VALID_EMAIL);
    }
}
