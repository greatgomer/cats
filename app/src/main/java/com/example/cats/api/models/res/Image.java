package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {
    @SerializedName("image_id")
    private String image_id;

    public Image(String image_id) {
        this.image_id = image_id;
    }

}
