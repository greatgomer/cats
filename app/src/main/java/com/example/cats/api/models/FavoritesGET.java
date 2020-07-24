//package com.example.cats.api.models;
//
//import com.google.gson.annotations.SerializedName;
//
//public class FavoritesGET {
//
//    @SerializedName("id")
//    private String id;
//    @SerializedName("image")
//    private FavoritesTest image;
//
//    public FavoritesGET( String id, FavoritesTest image) {
//        this.id = id;
//        this.image = image;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public FavoritesTest getName() {
//        return image;
//    }
//
//}

package com.example.cats.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesGET {

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    public FavoritesGET( String id, String url, int width, int height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height= height;
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