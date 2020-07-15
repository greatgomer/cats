package com.example.cats.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cat {

    @SerializedName("breeds")
    private List<Object> breeds;
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    public Cat(List<Object> breeds, String id, String url, int width, int height){
        this.breeds = breeds;
        this.id = id;
        this.url = url;
        this.width = width;
        this.height= height;
    }

    public List<Object> getBreads() {
        return breeds;
    }

    public void setBreads(List<Object> breeds) {
            this.breeds = breeds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
