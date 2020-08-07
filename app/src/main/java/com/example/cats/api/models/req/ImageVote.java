package com.example.cats.api.models.req;

public class ImageVote {
    final String image_id;
    final String sub_id;
    final Integer value;

    public ImageVote(String image_id, String sub_id, Integer value) {
        this.image_id = image_id;
        this.sub_id = sub_id;
        this.value = value;
    }
}
