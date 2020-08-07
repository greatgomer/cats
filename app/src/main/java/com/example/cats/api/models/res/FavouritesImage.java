package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class FavouritesImage {

    @SerializedName("url")
    private String url;
    @SerializedName("id")
    private String id;

    public FavouritesImage(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    public String getId() {return id; }

}
