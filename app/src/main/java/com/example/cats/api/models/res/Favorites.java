package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Favorites {

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public Favorites(String id, String url) {
        this.id = id;
        this.url = url;
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

}