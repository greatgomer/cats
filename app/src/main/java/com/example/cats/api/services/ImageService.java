package com.example.cats.api.services;

import com.example.cats.api.models.req.ImageVote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ImageService {
    @Headers("Content-type: application/json")
    @POST("votes")
    Call<ImageVote> imageVote (@Body ImageVote body);

}
