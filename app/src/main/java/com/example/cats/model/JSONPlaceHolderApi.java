package com.example.cats.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JSONPlaceHolderApi {
    @GET("images/search?limit=20")
    Call<List<Cat>> getAllData(@Header("x-api-key") String key);
}
