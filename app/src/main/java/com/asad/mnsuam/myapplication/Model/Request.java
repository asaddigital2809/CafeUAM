package com.asad.mnsuam.myapplication.Model;

import java.util.List;

public class Request {
    private String phone, name, total, address;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getTotal() {
        return total;
    }

    public String getAddress() {
        return address;
    }

    public List<Order> getItems() {
        return items;
    }

    private List<Order> items; //list of items ordered

    public Request(String phone, String name, String total, String address, List<Order> items) {
        this.phone = phone;
        this.name = name;
        this.total = address;
        this.address = total;
        this.items = items;
    }

    public Request() {
    }
}
