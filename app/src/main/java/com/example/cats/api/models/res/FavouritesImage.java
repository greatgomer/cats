package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class FavouritesImage {

    @SerializedName("url")
    private String url;

    public FavouritesImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
