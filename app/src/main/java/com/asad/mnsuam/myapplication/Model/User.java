package com.asad.mnsuam.myapplication.Model;

public class User {
    private String name;
    private String phone;
    private String password;
    private String address;
    private String email;
    private String isStaff;

    public User(){

    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String password, String address, String email) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.isStaff = "false";
    }
}