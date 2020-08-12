package com.example.cats;

import androidx.test.runner.AndroidJUnit4;

import com.example.cats.api.models.req.DeleteFromDownloads;
import com.example.cats.api.models.req.DeleteFromFavourites;
import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.models.res.Downloads;
import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.api.services.ImagesService;
import com.example.cats.api.services.NetworkProvider;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertNotNull;

/**
 * Instrumented image_background, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static HashMap<String, String> params = new HashMap<>();
    String sub_id = "test3";

    NetworkProvider retrofit = new NetworkProvider();
    ImagesService imagesService = retrofit.createImagesService();
    Call<List<Cat>> cats = imagesService.getAllCats(params);

    FavouritesService favouritesService = retrofit.createFavouritesService();

    Call<List<Favorites>> favourites = favouritesService.getAllFavorites(sub_id);

    FavoritesParameters favoritesParameters = new FavoritesParameters("1ud", sub_id);
    Call<FavoritesParameters> favoritesAdd = favouritesService.postFavourites(favoritesParameters);

    Call<DeleteFromFavourites> deleteFromFavouritesCall = favouritesService.deleteFromFavorites(1);
    Call<DeleteFromFavourites> favouritesDel = favouritesService.deleteFromFavorites(1);

    DownloadsService downloadsService = retrofit.createDownloadsService();
    Call<List<Downloads>> downloads = downloadsService.getAllDownloads(sub_id, 10);

    Call<DeleteFromDownloads> deleteFromDownloadsCall = downloadsService.deleteFromDownloads("1");

    @Test
    public void testGetAllCatsData() {
        assertNotNull(cats);
    }

    @Test
    public void testGetAllFavouritesData() {
        assertNotNull(favourites);
    }

    @Test
    public void testAddInFavourites(){
        assertNotNull(favoritesAdd);
    }

    @Test
    public void testDelFromFavourites(){
        assertNotNull(favouritesDel);
    }

    @Test
    public void testGetAllDownloadsData() {
        assertNotNull(downloads);
    }

    @Test
    public void testDataFromCats() {
        cats.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                assert response.body() != null;
                List<Cat> resultCats = new ArrayList<>(response.body());
                assertNotNull(resultCats.get(0).getId());
                assertNotNull(resultCats.get(0).getUrl());
            }

            @Override
            public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {

            }
        });
    }

    @Test
    public void testDataFromFavourites() {
        favourites.enqueue(new Callback<List<Favorites>>() {
            @Override
            public void onResponse(@NotNull Call<List<Favorites>> call, @NotNull Response<List<Favorites>> response) {
                assert response.body() != null;
                List<Favorites> resultCats = new ArrayList<>(response.body());
                assertNotNull(resultCats.get(0).getId());
                assertNotNull(resultCats.get(0).getImage());
            }

            @Override
            public void onFailure(@NotNull Call<List<Favorites>> call, @NotNull Throwable t) {

            }
        });
    }

    @Test
    public void testDataLoadToFavourites() {
        favoritesAdd.enqueue(new Callback<FavoritesParameters>() {
            @Override
            public void onResponse(@NotNull Call<FavoritesParameters> call, @NotNull Response<FavoritesParameters> response) {
                assertNotNull(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<FavoritesParameters> call, @NotNull Throwable t) {
            }
        });
    }

    @Test
    public void testDataDeleteFromFavourites() {
        deleteFromFavouritesCall.enqueue(new Callback<DeleteFromFavourites>() {
            @Override
            public void onResponse(@NotNull Call<DeleteFromFavourites> call, @NotNull Response<DeleteFromFavourites> response) {
                assertNotNull(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<DeleteFromFavourites> call, @NotNull Throwable t) {

            }
        });
    }

        @Test
    public void testDataFromDownloads() {
        downloads.enqueue(new Callback<List<Downloads>>() {
            @Override
            public void onResponse(@NotNull Call<List<Downloads>> call, @NotNull Response<List<Downloads>> response) {
                assert response.body() != null;
                assertNotNull(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<Downloads>> call, @NotNull Throwable t) {

            }
        });
    }

    @Test
    public void testDeleteFromDownloads() {
        deleteFromDownloadsCall.enqueue(new Callback<DeleteFromDownloads>() {
            @Override
            public void onResponse(@NotNull Call<DeleteFromDownloads> call, @NotNull Response<DeleteFromDownloads> response) {
                assertNotNull(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<DeleteFromDownloads> call, @NotNull Throwable t) {

            }
        });
    }

}