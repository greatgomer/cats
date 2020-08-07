package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class ImageInfo {
    @SerializedName("description")
    private String description;

    public ImageInfo(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
