package com.example.phuotogether.dto;
import java.io.Serializable;
public class User implements Serializable{
    private int id;
    private boolean loginType;
    private String emailOrTel;
    private String password;
    private String fullName;

    // Constructor
    public User(int id, boolean loginType, String emailOrTel, String password, String fullName) {
        this.id = id;
        this.loginType = loginType;
        this.emailOrTel = emailOrTel;
        this.password = password;
        this.fullName = fullName;
    }

    // Getter
    public int getId() {
        return id;
    }
    public boolean getLoginType() {
        return loginType;
    }
    public String getEmailOrTel() {
        return emailOrTel;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setLoginType(boolean loginType) {
        this.loginType = loginType;
    }
    public void setEmailOrTel(String emailOrTel) {
        this.emailOrTel = emailOrTel;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
