package com.aplication.onlinepharmacy.models;

public class OrderItem {

    private int id;
    private String productImage;
    private  int quantity;


    public OrderItem(int id, String productImage, int quantity) {
        this.id = id;
        this.productImage = productImage;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
