package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {
    @SerializedName("breeds")
    private List breeds;

    public Image(List breeds) {
        this.breeds = breeds;
    }

}
