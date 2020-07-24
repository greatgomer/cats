package com.example.cats.api.services;

import com.example.cats.api.models.Cat;
import com.example.cats.api.models.FavoritesGET;
import com.example.cats.api.models.FavoritesPOST;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ImagesService {
    @GET("images/search")
//    Observable<List<Cat>> getAllData(@QueryMap Map<String, String> parameters);
    Call<List<Cat>> getAllCats(@QueryMap Map<String, String> parameters);

    @GET("images/search?limit=14")
    Call<List<FavoritesGET>> getAllFavorites();

    @POST("favourites")
    Call<FavoritesPOST> postJson(@Body FavoritesPOST body);

}