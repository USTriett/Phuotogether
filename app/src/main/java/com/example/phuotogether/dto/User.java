package com.example.phuotogether.dto;
import java.io.Serializable;
public class User implements Serializable {
    private static User instance;

    private String fullName;
    private int id;
    private boolean loginType; // true is email, false is phone number
    private String email;
    private String phoneNumber;
    private String password;

    // Private constructor to prevent instantiation from outside the class
    private User() {
        this.id = 0;
        this.loginType = true;
        this.phoneNumber = "0000000000";
        this.email = "LTMP@gmail.com";
        this.password = "123456";
        this.fullName = "Le Thi Minh Phuong";
    }

    // Public method to get the instance of the singleton class
    public static User getInstance(
            int id, boolean loginType, String email, String password, String fullName
    ) {
        if (instance == null) {
            instance = new User();


        }
        instance.updateInfo(id, loginType, email, password, fullName);
        return instance;
    }

    public static User getInstance(){
        if (instance == null) {
            instance = new User();

        }
        return instance;
    }

    // Other methods...

    public void updateInfo(
            int id, boolean loginType, String email, String password, String fullName
    ) {

        instance.id = id;
        instance.loginType = loginType;
        instance.email = email;
        instance.password = password;
        instance.fullName = fullName;
    }

    // Other getters and setters...
    public int getId() {
        return id;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getPassword() {
        return password;
    }

    public String getFullName(){
        return this.fullName;
    }
    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setLoginType(boolean loginType) {
        this.loginType = loginType;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // You might want to override clone and throw CloneNotSupportedException to prevent cloning

}
