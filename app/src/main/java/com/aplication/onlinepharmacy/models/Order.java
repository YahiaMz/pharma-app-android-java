package com.aplication.onlinepharmacy.models;

import java.util.ArrayList;

public class Order {

    private  int id;
    private ArrayList<OrderItem> items;
    private int totalPrice;
    private String addressName;
    private int status;
    private  boolean isActive ;

    public Order(int id, ArrayList<OrderItem> items, String addressName, int status, boolean isActive , int totalPrice) {
        this.id = id;
        this.items = items;
        this.addressName = addressName;
        this.status = status;
        this.isActive = isActive;
        this.totalPrice = totalPrice;
    }

    public boolean isActive() {
        return isActive;
    }
    public int getId() {
        return id;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getAddressName() {
        return addressName;
    }

    public int getStatus() {
        return status;
    }
}
