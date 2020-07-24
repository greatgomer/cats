package com.example.cats.api.models;

import com.google.gson.annotations.SerializedName;

public class FavoritesTest {

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public FavoritesTest(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}