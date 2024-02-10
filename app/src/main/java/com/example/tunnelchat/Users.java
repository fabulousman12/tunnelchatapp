package com.example.tunnelchat;

public class Users {
     String userId;
     String username;
     String email;
     String password;
     String status;

     String profilepic;
    String phoneNumberr;


     public Users(){

     }
    public Users(String id, String namee, String emaill, String password, String imageuri, String status , String phoneNumber) {
        this.userId = id;
        this.username = namee;
        this.email = emaill;
        this.password = password;
        this.status = status;
        this.profilepic = imageuri;
        this.phoneNumberr=phoneNumber;

    }

    public String getPhoneNumberr() {
        return phoneNumberr;
    }

    public void setPhoneNumberr(String phoneNumberr) {
        this.phoneNumberr = phoneNumberr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

}
