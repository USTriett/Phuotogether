package com.example.phuotogether.dto;
import java.io.Serializable;
public class User implements Serializable{
    private int id;
    private boolean loginType;
    private String email;

    private String phoneNumber;
    private String password;
    private String fullName;
    private String firstName;
    private String lastName;

    // Constructor
    public User(int id, boolean loginType, String emailOrTel, String password, String fullName) {
        this.id = id;
        this.loginType = loginType;
        this.phoneNumber = "";
        this.email = "";

        if(emailOrTel.contains("@")){
            this.email = emailOrTel;
        }
        else{
            this.phoneNumber = emailOrTel;
        }
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
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User(){
        this.id = 0;
        this.loginType = true;
        this.phoneNumber = "0000000000";
        this.email = "LTMP@gmail.com";

        this.password = "123456";
        this.fullName = "Le Thi Minh Phuong";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateInfo(String firstName, String lastName, String phoneNumber, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.fullName = username;
    }
}
