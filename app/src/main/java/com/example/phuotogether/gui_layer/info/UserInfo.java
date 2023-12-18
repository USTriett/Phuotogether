package com.example.phuotogether.gui_layer.info;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String _firstName;
    private String _lastName;
    private String _phoneNumber;
    private String _username;
//    UserController controller;

    UserInfo(String firstName, String lastName, String phoneNumber, String username){
        this._firstName = firstName;
        this._lastName = lastName;
        this._phoneNumber = phoneNumber;
        this._username = username;
    }

    public UserInfo() {
        this._firstName = "";
        this._lastName = "";
        this._phoneNumber = "";
        this._username = "";
    }

    public void updateUserInfo(String firstName, String lastName, String phoneNumber, String username){
        this._firstName = firstName;
        this._lastName = lastName;
        this._phoneNumber = phoneNumber;
        this._username = username;
    }

    public String getUsername() {
        return _username;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public String getFirstName() {
        return _firstName;
    }
    public String getLastName(){
        return _lastName;
    }
}
