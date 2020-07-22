package com.example.cats.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JSONPlaceHolderApi {
    @GET
    Call<List<Cat>> getAllData(@Url String anEmptyString);

//    @GET("images/search")
//    Call<List<Cat>> getAllData(@QueryMap Map<String, String> parameters);
}
