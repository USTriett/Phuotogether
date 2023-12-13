package com.example.phuotogether.data_layer.auth.SignIn;

public class UserDatabase {
    private static final String VALID_EMAIL = "haisonqn2003@gmail.com";
    private static final String VALID_PASSWORD = "Haison@2003";

    public boolean isSuccessSignIn(String email, String password) {
        return email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD);
    }
}
