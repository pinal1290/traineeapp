package com.example.softices;

import android.graphics.Bitmap;

class UserModel {
    private byte[] image;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String address;
    private String city;
    private String pincode;
    private String password;

    byte[] getimage() {
        return image;
    }

    void setImage(byte[] image) {
        this.image = image;
    }

    String getFirstname() {
        return firstname;
    }

    void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    String getLastname() {
        return lastname;
    }

    void setLastname(String lastname) {
        this.lastname = lastname;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getGender() {
        return gender;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    String getPincode() {
        return pincode;
    }

    void setPincode(String pincode) {
        this.pincode = pincode;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }


}


