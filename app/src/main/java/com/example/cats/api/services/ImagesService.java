package com.example.cats.api.services;

import com.example.cats.api.models.res.Cat;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ImagesService {
    @GET("images/search")
//    Observable<List<Cat>> getAllData(@QueryMap Map<String, String> parameters);
    Call<List<Cat>> getAllCats(@QueryMap Map<String, String> parameters);

}