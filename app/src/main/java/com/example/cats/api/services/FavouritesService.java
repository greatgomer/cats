package com.example.cats.api.services;

import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Favorites;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavouritesService {
    @GET("favourites")
    Call<List<Favorites>> getAllFavorites();

    @POST("favourites")
    Call<FavoritesParameters> postFavourites(@Body FavoritesParameters body);

    @DELETE("favourites/{favourite_id}")
    Call<DeleteFromFavourites> deleteFromFavorites(@Path("favourite_id") Integer body);

}
