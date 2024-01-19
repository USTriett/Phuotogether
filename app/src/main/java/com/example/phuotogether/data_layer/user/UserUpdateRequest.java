package com.example.phuotogether.data_layer.user;

public class UserUpdateRequest {
    private int id;
    private String password;
    private String fullname;

    public UserUpdateRequest(int id, String password, String fullname) {
        this.id = id;
        this.password = password;
        this.fullname = fullname;
    }

    // Getters and setters
}