package com.aplication.onlinepharmacy.models;

public class Category {

    private  int id;
    private  String name;
    private  String svgImageUrl;

    public Category(int id, String name, String svgImageUrl) {
        this.id = id;
        this.name = name;
        this.svgImageUrl = svgImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSvgImageUrl() {
        return svgImageUrl;
    }

    public void setSvgImageUrl(String svgImageUrl) {
        this.svgImageUrl = svgImageUrl;
    }
}
