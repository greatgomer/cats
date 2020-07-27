package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Favorites {

    @SerializedName("image")
    private FavouritesImage image;

    public Favorites(FavouritesImage image) {
        this.image = image;
    }

    public FavouritesImage getImage() {
        return image;
    }

}