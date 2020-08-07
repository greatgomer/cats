package com.example.cats.api.services;

import com.example.cats.api.models.req.ImageVote;
import com.example.cats.api.models.res.Image;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ImageService {
    @GET("images/{image_id}")
    Call<Image> image(@Path("image_id") String image_id);

    @Headers("Content-type: application/json")
    @POST("votes")
    Call<ImageVote> imageVote (@Body ImageVote body);

}
