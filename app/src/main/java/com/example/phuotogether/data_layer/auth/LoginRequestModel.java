package com.example.phuotogether.data_layer.auth;

public class LoginRequestModel {
    private String emailortel;
    private String password;

    public LoginRequestModel(String emailortel, String password) {
        this.emailortel = emailortel;
        this.password = password;
    }
}