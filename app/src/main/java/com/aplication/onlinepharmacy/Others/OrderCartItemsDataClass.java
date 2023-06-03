package com.aplication.onlinepharmacy.Others;

public class OrderCartItemsDataClass {
     private int address_Id   ;
     private String paymentMethod;
     private String phoneNumber;
     private int shippingPrice;

    public OrderCartItemsDataClass(int address_Id, String paymentMethod, String phoneNumber, int shippingPrice) {
        this.address_Id = address_Id;
        this.paymentMethod = paymentMethod;
        this.phoneNumber = phoneNumber;
        this.shippingPrice = shippingPrice;
    }


    public int getAddress_Id() {
        return address_Id;
    }

    public void setAddress_Id(int address_Id) {
        this.address_Id = address_Id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
}
