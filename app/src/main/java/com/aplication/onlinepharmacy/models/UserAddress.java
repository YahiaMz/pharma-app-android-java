package com.aplication.onlinepharmacy.models;

public class UserAddress {
    private  int id ;
    private String name ;
    private  String address;
    private boolean isSelected;

    public UserAddress(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isSelected = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
