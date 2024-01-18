package com.example.phuotogether.data_layer.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("emailortel")
    @Expose
    private String emailOrTel;

    @SerializedName("logintype")
    @Expose
    private boolean loginType;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("fullname")
    @Expose
    private String fullName;

    // Constructors, getters, and setters

    public UserResponse(int id, String emailOrTel, boolean loginType, String password, String fullName) {
        this.id = id;
        this.emailOrTel = emailOrTel;
        this.loginType = loginType;
        this.password = password;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getEmailOrTel() {
        return emailOrTel;
    }

    public boolean isLoginType() {
        return loginType;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}
