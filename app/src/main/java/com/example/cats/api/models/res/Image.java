package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("breeds")
    private ImageInfo[] breeds;

    public Image(ImageInfo[] breeds) {
        this.breeds = breeds;
    }
    public ImageInfo[] getBreeds() {
        return breeds;
    }

}
