package com.example.cats.api.models.req;

public class FavoritesParameters {
    String image_id;
    String sub_id;

    public FavoritesParameters(String image_id, String sub_id) {
        this.image_id = image_id;
        this.sub_id = sub_id;
    }

}
