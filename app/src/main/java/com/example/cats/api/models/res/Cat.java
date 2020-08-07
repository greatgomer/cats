package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Cat {

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public Cat(String id, String url) {
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
