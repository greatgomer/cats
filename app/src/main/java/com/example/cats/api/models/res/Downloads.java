package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Downloads {

    @SerializedName("url")
    private String url;

    public Downloads(String url) {
        this.url = url;
    }

    public String getUrl() { return url; }

}
