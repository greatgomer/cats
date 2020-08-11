package com.example.cats;

import androidx.test.runner.AndroidJUnit4;

import com.example.cats.api.models.req.FavoritesParameters;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.models.res.Downloads;
import com.example.cats.api.models.res.Favorites;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.api.services.FavouritesService;
import com.example.cats.api.services.ImagesService;
import com.example.cats.api.services.NetworkProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented image_background, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static HashMap<String, String> params = new HashMap<>();
    HttpsURLConnection myConnection;
    String sub_id = "test3";
    URL api;

    NetworkProvider retrofit = new NetworkProvider();
    ImagesService imagesService = retrofit.createImagesService();
    Call<List<Cat>> cats = imagesService.getAllCats(params);

    FavouritesService favouritesService = retrofit.createFavouritesService();

    Call<List<Favorites>> favourites = favouritesService.getAllFavorites(sub_id);

    FavoritesParameters favoritesParameters = new FavoritesParameters("1ud", sub_id);
    Call<FavoritesParameters> favoritesAdd = favouritesService.postFavourites(favoritesParameters);

    DownloadsService downloadsService = retrofit.createDownloadsService();
    Call<List<Downloads>> downloads = downloadsService.getAllDownloads(sub_id, 10);

    @Before
    public void catsConnect() throws IOException {
        api = new URL("https://api.thecatapi.com/v1/images/search");
        myConnection =
                (HttpsURLConnection) api.openConnection();
        myConnection.setRequestProperty("x-api-key", "97a76886-9a72-4bb1-81a2-e730833ffbdb");
    }

    @Test
    public void testCatsConnect() throws IOException {
        assertEquals(200, myConnection.getResponseCode());
    }

    @Before
    public void favouritesConnect() throws IOException {
        api = new URL("https://api.thecatapi.com/v1/favourites");
        myConnection =
                (HttpsURLConnection) api.openConnection();
        myConnection.setRequestProperty("x-api-key", "97a76886-9a72-4bb1-81a2-e730833ffbdb");
    }

    @Test
    public void testFavouritesConnect() throws IOException {
        assertEquals(200, myConnection.getResponseCode());
    }

    @Before
    public void downloadsConnect() throws IOException {
        api = new URL("https://api.thecatapi.com/v1/downloads");
        myConnection =
                (HttpsURLConnection) api.openConnection();
        myConnection.setRequestProperty("x-api-key", "97a76886-9a72-4bb1-81a2-e730833ffbdb");
    }

    @Test
    public void testDownloadsConnect() throws IOException {
        assertEquals(200, myConnection.getResponseCode());
    }

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
    public void testGetAllDownloadsData() {
        assertNotNull(downloads);
    }

}