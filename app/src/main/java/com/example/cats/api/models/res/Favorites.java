package com.example.cats.api.models.res;

import com.google.gson.annotations.SerializedName;

public class Favorites {

    @SerializedName("id")
    private Integer id;
    @SerializedName("image")
    private FavouritesImage image;

    public Favorites(Integer id, FavouritesImage image) {
        this.id = id;
        this.image = image;
    }

    public Integer getId() { return id; }
    public FavouritesImage getImage() {
        return image;
    }

}