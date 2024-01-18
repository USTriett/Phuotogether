package com.example.phuotogether.data_layer.auth;

public class SignupRequestModel {
    private String emailortel;
    private boolean logintype;
    private String password;
    private String fullname;

    public SignupRequestModel(String emailortel, boolean logintype, String password, String fullname) {
        this.emailortel = emailortel;
        this.logintype = logintype;
        this.password = password;
        this.fullname = fullname;
    }

}
