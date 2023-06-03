package com.aplication.onlinepharmacy.models;

public class Product {

    private  int id;
    private  int category_Id ;
    private  String name;
    private  String description;
    private  String imageUrl ;
    private  int price;
    private  boolean isAvailable ;
    private boolean isLike ;

    public Product(int id, int category_Id, String name, String description, String imageUrl, int price, boolean isAvailable, boolean isLike) {
        this.id = id;
        this.category_Id = category_Id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isAvailable = isAvailable;
        this.isLike = isLike;
    }

    public Product(int id, int category_Id, String name, String description, String imageUrl, int price, boolean isAvailable) {
        this.id = id;
        this.category_Id = category_Id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Product(int id, String name, String description, String imageUrl, int price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isAvailable = isAvailable;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
