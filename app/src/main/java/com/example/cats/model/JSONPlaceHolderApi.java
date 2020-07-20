package com.example.cats.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {
    @GET("images/search?limit=20")
    Call<List<Cat>> getAllData();
}
