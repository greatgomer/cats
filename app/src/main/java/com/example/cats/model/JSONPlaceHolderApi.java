package com.example.cats.model;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface JSONPlaceHolderApi {
    @GET("images/search")
    Observable<List<Cat>> getAllData(@QueryMap Map<String, String> parameters);
}